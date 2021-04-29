package com.twokeys.moinho.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twokeys.moinho.dto.ProductDTO;
import com.twokeys.moinho.dto.ProductionOrderItemDTO;
import com.twokeys.moinho.dto.StockMovementDTO;
import com.twokeys.moinho.entities.Parameter;
import com.twokeys.moinho.entities.Product;
import com.twokeys.moinho.entities.ProductionOrder;
import com.twokeys.moinho.entities.ProductionOrderItem;
import com.twokeys.moinho.entities.enums.CostType;
import com.twokeys.moinho.entities.enums.ProductionOrderItemType;
import com.twokeys.moinho.entities.enums.StockMovementType;
import com.twokeys.moinho.repositories.OccurrenceRepository;
import com.twokeys.moinho.repositories.ParameterRepository;
import com.twokeys.moinho.repositories.ProductRepository;
import com.twokeys.moinho.repositories.ProductionOrderItemRepository;
import com.twokeys.moinho.repositories.ProductionOrderRepository;
import com.twokeys.moinho.services.exceptions.DatabaseException;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;
@Service
public class ProductionOrderItemService {
	protected final Log logger = LogFactory.getLog(getClass());
	@Autowired
	private ProductionOrderItemRepository repository;
	@Autowired 
	private ProductRepository productRepository; 
	@Autowired
	private ProductionOrderRepository productionOrderRepository;
	@Autowired
	private OccurrenceRepository occurrenceRepository;
	@Autowired
	private StockMovementService stockMovementService;
	@Autowired
	private ParameterRepository parameterRepository;
	
	@Transactional
	public List<ProductionOrderItemDTO> insert(List<ProductionOrderItemDTO> dto) {
		try {
			Parameter parameter = parameterRepository.findById(1L).get();
			Product product;
			ProductionOrderItem entity = new ProductionOrderItem();
			List<ProductionOrderItem> entityList = new ArrayList<>();
			ProductionOrder order = new ProductionOrder();
			StockMovementDTO stockMovement;
			order.setId(dto.get(0).getProductionOrderId());
			Integer serie = repository.findMaxSerie(order) + 1;
			
			for(ProductionOrderItemDTO itemDto : dto){
				product = productRepository.findById(itemDto.getProduct().getId()).get();
				/*Validar estoque*/
				if (parameter.isProductionOrderWithoutStock()==false && itemDto.getType()!=ProductionOrderItemType.RETORNO  ) {
					if (product.getStockBalance() < itemDto.getQuantity()) {
						throw new ResourceNotFoundException("Product id: "+itemDto.getProduct().getId() +" - " + itemDto.getProduct().getDescription() + " out of stock");
					}
				}
				entity = new ProductionOrderItem();
				itemDto.setSerie(serie);
				entity= convertToEntity(itemDto);
				
				/*Validar tipo de custo*/
				if (parameter.getTypeCostUsed()==CostType.CUSTO_MEDIO) {	
					entity.setCost(product.getAverageCost());
				}else {
					entity.setCost(product.getCostLastEntry());
				}
				entity.setProduct(product);
				/*Faz a inserção no estoque recuperando o ID*/
				stockMovement = new StockMovementDTO();
				if (itemDto.getType()==ProductionOrderItemType.RETORNO) {
					stockMovement.setCost(entity.getCost());
					stockMovement.setEntry(entity.getQuantity());
					stockMovement.setOut(0.0);
					stockMovement.setDescription("");
					stockMovement.setIdOrignMovement(entity.getProductionOrder().getId());
					stockMovement.setType(StockMovementType.PRODUCAO_RETORNO);
					stockMovement.setProduct(new ProductDTO(product));
					stockMovement=stockMovementService.insert(stockMovement);
				}else {
					stockMovement.setCost(entity.getCost());
					stockMovement.setOut(entity.getQuantity());
					stockMovement.setEntry(0.0);
					stockMovement.setDescription("");
					stockMovement.setIdOrignMovement(entity.getProductionOrder().getId());
					stockMovement.setType(StockMovementType.PRODUCAO_CONSUMO);
					stockMovement.setProduct(new ProductDTO(product));
					stockMovement=stockMovementService.insert(stockMovement);
				}
				entity.setStockId(stockMovement.getId());
				entityList.add(entity);
			}
			entityList = repository.saveAll(entityList);
			dto.clear();
			/*Realiza o consumo no estoque*/
			for(ProductionOrderItem item: entityList) {
				dto.add(new ProductionOrderItemDTO(item));
			}	
			return dto;
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found");
		}catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Id not found");
		}catch(ConstraintViolationException e) {
			throw new DatabaseException("Id not found");
		}
	}
	
	public ProductionOrderItem convertToEntity(ProductionOrderItemDTO dto) {
			ProductionOrderItem entity = new ProductionOrderItem(); 
			entity.setSerie(dto.getSerie());
			entity.setProductionOrder(productionOrderRepository.getOne(dto.getProductionOrderId()));
			entity.setQuantity(dto.getQuantity());
			entity.setCost(dto.getCost());
			entity.setType(dto.getType());
			entity.setOccurrence(occurrenceRepository.getOne(dto.getOccurrence().getId()));
			entity.setRawMaterial(dto.getRawMaterial());
			return entity;
	}
}
