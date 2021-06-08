package com.twokeys.moinho.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twokeys.moinho.dto.FormulationDTO;
import com.twokeys.moinho.dto.FormulationItemDTO;
import com.twokeys.moinho.dto.OccurrenceDTO;
import com.twokeys.moinho.dto.ProductionOrderDTO;
import com.twokeys.moinho.dto.ProductionOrderItemDTO;
import com.twokeys.moinho.entities.Formulation;
import com.twokeys.moinho.entities.ProductionOrder;
import com.twokeys.moinho.entities.ProductionOrderItem;
import com.twokeys.moinho.entities.ProductionOrderProduced;
import com.twokeys.moinho.entities.enums.FormulationItemType;
import com.twokeys.moinho.entities.enums.FormulationType;
import com.twokeys.moinho.entities.enums.ProductionOrderItemType;
import com.twokeys.moinho.entities.enums.ProductionOrderStatus;
import com.twokeys.moinho.repositories.FormulationRepository;
import com.twokeys.moinho.repositories.ProductionOrderRepository;
import com.twokeys.moinho.services.exceptions.DatabaseException;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;
import com.twokeys.moinho.services.exceptions.UntreatedException;



@Service
public class ProductionOrderService {
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private ProductionOrderRepository repository;
	
	@Autowired
	private FormulationRepository formulationRepository;
	
	@Autowired
	private FormulationService formulationService;
	
	@Autowired
	private ProductionOrderItemService productionOrderItemsService;
	@Autowired
	private StockMovementService stockMovementService;
	
	
	@Transactional
	public ProductionOrderDTO createProductionOrder(Long formulationId, Double ammount,Boolean persistence, Instant startDate){
		FormulationDTO formulation = formulationService.findById(formulationId);
		ProductionOrderDTO productionOrder = new ProductionOrderDTO();
		ProductionOrderItemDTO productionOrderItems; 
		OccurrenceDTO occurrence = new OccurrenceDTO(1L,"NORMAL");
		Double quantityItem =0.0;
		
		for (FormulationItemDTO formulationItems : formulation.getFormulationItems()) {
			if(formulationItems.getType() == FormulationItemType.NORMAL) {
				occurrence = new OccurrenceDTO(1L,"NORMAL");
				quantityItem= (ammount*formulationItems.getQuantity())/formulation.getCoefficient();
				productionOrderItems = new ProductionOrderItemDTO();
				productionOrderItems.setOccurrence(occurrence);
				productionOrderItems.setRawMaterial(formulationItems.getRawMaterial());
				if (formulationItems.getRound()) {
					productionOrderItems.setQuantity( Double.valueOf(new BigDecimal(quantityItem).setScale(0,RoundingMode.UP).toString()));
				}else {
					productionOrderItems.setQuantity(Double.valueOf(new BigDecimal(quantityItem).setScale(2,RoundingMode.HALF_UP).toString()));
				}
				productionOrderItems.setType(ProductionOrderItemType.NORMAL);
				productionOrderItems.setProduct(formulationItems.getProduct());
				productionOrderItems.setCost(formulation.getProduct().getAverageCost());
				productionOrder.getProductionOrderItems().add(productionOrderItems);
			}
			
		}
		
		productionOrder.setEmission(Instant.now());
		productionOrder.setStartDate(startDate);
		productionOrder.setExpectedAmount(ammount);
		productionOrder.setStatus(ProductionOrderStatus.ABERTO);
		productionOrder.setFormulation(formulation);
		
		if (persistence) {
			return insert(productionOrder);
		}else {
			return productionOrder;
		}
		 
	}
		
	@Transactional(readOnly=true)
	public Page<ProductionOrderDTO> findByStartDateAndFormulation(Long formulationId,Instant startDate,Instant endDate,PageRequest pageRequest){
		Formulation formulation = (formulationId==0) ? null : formulationRepository.getOne(formulationId);
		Page<ProductionOrder> page = repository.findByStartDateAndFormulation(formulation,startDate,endDate,pageRequest);
		return page.map(x -> new ProductionOrderDTO(x));
	}
	 
	@Transactional(readOnly=true)
	public ProductionOrderDTO findById(Long id){
		ProductionOrder obj = repository.findByIdAndDateCancelIsNull(id);
		if (obj== null) {
		  throw	new ResourceNotFoundException("Entity not found");
		}
		return new ProductionOrderDTO(obj);
	}
	@Transactional(readOnly=true)
	public List<ProductionOrderDTO> listByStartDateAndStatus(Instant startDate,Instant endDate, ProductionOrderStatus status, FormulationType type){
		List<ProductionOrder> list = repository.findByStartDateBetweenAndStatusAndFormulationTypeOrderByFormulationLevelAsc(startDate, endDate, status,type);
	    return list.stream().map(x -> new ProductionOrderDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional
	public ProductionOrderDTO insert(ProductionOrderDTO dto) {
		try {
			ProductionOrder entity =new ProductionOrder();
			convertToEntity(dto, entity);
			entity=repository.save(entity);
			
			List<ProductionOrderItemDTO> list = new ArrayList<>();
			
			for(ProductionOrderItemDTO item : dto.getProductionOrderItems()) {
				item.setProductionOrderId(entity.getId());
			}
			
			list=productionOrderItemsService.insert(dto.getProductionOrderItems());
			
			return new ProductionOrderDTO(entity,list);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found");
		}catch(Exception e) {
			throw new UntreatedException(e.getMessage());
		}
	}
	
	@Transactional
	public ProductionOrderDTO update(Long id, ProductionOrderDTO dto) {
		try {
			ProductionOrder entity = repository.getOne(id);
			
			entity.setStartDate(dto.getStartDate());
			entity.setEndDate(dto.getEndDate());
			entity.setObservation(dto.getObservation());
			
			if (entity.getStatus() != ProductionOrderStatus.APURACAO_FINALIZADA) {
				if(entity.getStatus()==ProductionOrderStatus.ABERTO && dto.getEndDate() != null  ) {
					entity.setStatus(ProductionOrderStatus.ENCERRADO);
				}else {
					if(dto.getEndDate()==null) {
						entity.setStatus(ProductionOrderStatus.ABERTO);
					}
				}
			}else {
				throw new ValidationException("Ordem de Produção com encerramento finalizado");
			}
			
			entity = repository.save(entity);
			return new ProductionOrderDTO(entity);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		}catch(Exception e) {
			throw new UntreatedException("untreated exception: " + e.getMessage());
		}
	}
	@Transactional
	public void delete(Long id) {
		try {
			ProductionOrder entity = repository.getOne(id);
			/*REMOVER MOVIMENTAÇÃO DE ESTOQUE DE CONSUMO*/
			for (ProductionOrderItem item : entity.getProductionOrderItems()) {
				stockMovementService.delete(item.getStockId());
			}
			/*REMOVER MOVIMENTAÇÃO DE ESTOQUE DE PRODUÇÃO*/
			for (ProductionOrderProduced item : entity.getProductionOrderProduceds()) {
				stockMovementService.delete(item.getStockId());
			}
			entity.setStatus(ProductionOrderStatus.CANCELADO);
			entity.setDateCancel(Instant.now());
			repository.save(entity);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		}catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}catch(ResourceNotFoundException e) {
			throw	new ResourceNotFoundException("Entity not found");
		}
	}
	
	public void convertToEntity(ProductionOrderDTO dto, ProductionOrder entity) {
		entity.setEmission(dto.getEmission());
		entity.setEndDate(dto.getEndDate());
		entity.setStartDate(dto.getStartDate());
		entity.setExpectedAmount(dto.getExpectedAmount());
		entity.setObservation(dto.getObservation());
		entity.setStatus(dto.getStatus());
		entity.setFormulation(formulationRepository.getOne(dto.getFormulation().getId()));
	}
}
