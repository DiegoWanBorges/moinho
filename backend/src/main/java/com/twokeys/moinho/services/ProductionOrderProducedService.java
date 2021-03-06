package com.twokeys.moinho.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twokeys.moinho.dto.ProductDTO;
import com.twokeys.moinho.dto.ProductionOrderProducedAverageCostDTO;
import com.twokeys.moinho.dto.ProductionOrderProducedDTO;
import com.twokeys.moinho.dto.StockMovementDTO;
import com.twokeys.moinho.entities.ProductionOrder;
import com.twokeys.moinho.entities.ProductionOrderProduced;
import com.twokeys.moinho.entities.StockMovement;
import com.twokeys.moinho.entities.enums.ProductionOrderStatus;
import com.twokeys.moinho.entities.enums.StockMovementType;
import com.twokeys.moinho.entities.pk.ProductionOrderProducedPK;
import com.twokeys.moinho.repositories.PalletStatusRepository;
import com.twokeys.moinho.repositories.ProductRepository;
import com.twokeys.moinho.repositories.ProductionOrderProducedRepository;
import com.twokeys.moinho.repositories.ProductionOrderRepository;
import com.twokeys.moinho.repositories.StockMovementRepository;
import com.twokeys.moinho.services.exceptions.DatabaseException;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;
import com.twokeys.moinho.services.exceptions.UntreatedException;
import com.twokeys.moinho.util.Util;
@Service
public class ProductionOrderProducedService {
	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private ProductionOrderProducedRepository repository;
	@Autowired 
	private ProductRepository productRepository; 
	@Autowired
	private ProductionOrderRepository productionOrderRepository;
	@Autowired
	private PalletStatusRepository palletStatusRepository;
	@Autowired
	private StockMovementService stockMovementService;	
	@Autowired
	private StockMovementRepository stockMovementRepository;	
	
	
	@Transactional(readOnly=true)
	public ProductionOrderProducedDTO findById(Long productionOrderId, Integer pallet) {
		try {
			ProductionOrder productionOrder = productionOrderRepository.getOne(productionOrderId);
			ProductionOrderProducedPK pk = new ProductionOrderProducedPK(productionOrder, pallet);
			ProductionOrderProduced entity = repository.getOne(pk);
			return new ProductionOrderProducedDTO(entity);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: " + productionOrderId);
		} catch (Exception e) {
			throw new UntreatedException(e.getMessage());
		}
		
	}
	
	
	
	@Transactional(readOnly=true)
	public ProductionOrderProducedAverageCostDTO findProducedByProductAndStartDate(Long productId, Instant startDate,Instant endDate){
		try {
			List<Object[]> object= repository.findProducedByProductAndStartDate(productId,startDate,endDate);
			ProductionOrderProducedAverageCostDTO dto = new ProductionOrderProducedAverageCostDTO();
			dto.setId(productId);
			if (object.size() > 0) {
				dto.setName((String)object.get(0)[1]);
				dto.setUnity((String)object.get(0)[2]);
				dto.setTotalProduced(Util.roundHalfUp2((Double)object.get(0)[3]));
				dto.setAverageCost(Util.roundHalfUp2((Double)object.get(0)[4]));
			}else {
				dto.setTotalProduced(0.0);
				dto.setAverageCost(0.0);
			}
			return dto;
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: " + productId);
		} catch (Exception e) {
			throw new UntreatedException(e.getMessage());
		}
	}
	@Transactional(readOnly=true)
	public List<ProductionOrderProducedAverageCostDTO> findProducedStartDate(Instant startDate,Instant endDate){
		try {
			List<Object[]> object= repository.findProducedByStartDate(startDate,endDate);
			List<ProductionOrderProducedAverageCostDTO> list = new ArrayList<>();
			ProductionOrderProducedAverageCostDTO dto;
			
			for (int i = 0; i < object.size(); i++) {
				dto  = new ProductionOrderProducedAverageCostDTO();
				dto.setId((Long)object.get(i)[0]);
				dto.setName((String)object.get(i)[1]);
				dto.setUnity((String)object.get(i)[2]);
				dto.setTotalProduced(Util.roundHalfUp2((Double)object.get(i)[3]));
				dto.setAverageCost(Util.roundHalfUp2((Double)object.get(i)[4]));
				list.add(dto);
			}
						
			return list;
		} catch (Exception e) {
			throw new UntreatedException("Erro n??o esperado em calcular custo m??dio");
		}
	}
	
	@Transactional
	public ProductionOrderProducedDTO insert(ProductionOrderProducedDTO dto) {
		try {
			ProductionOrderProduced entity = new ProductionOrderProduced();
			ProductionOrder productionOrder = productionOrderRepository.getOne(dto.getProductionOrderId());
			
			if (productionOrder.getStatus() != ProductionOrderStatus.ABERTO) {
				throw new ValidationException("Produ????o com status " + productionOrder.getStatus() + " n??o pode ser alterada");
			}
			
			/*Recupera o proximo pallet*/
			Integer pallet = repository.findMaxPallet(productionOrder) + 1;
			dto.setPallet(pallet);
			convertToEntity(dto, entity);

			/*Inserir produ????o na movimenta????o de estoque*/
			StockMovementDTO stockMovement = new StockMovementDTO();
			stockMovement.setDate(LocalDate.ofInstant(dto.getManufacturingDate(), ZoneId.of("America/Sao_Paulo")));
			stockMovement.setCost(0.0);
			stockMovement.setOut(0.0);
			stockMovement.setEntry(entity.getQuantity());
			stockMovement.setDescription("");
			stockMovement.setIdOrignMovement(entity.getProductionOrder().getId());
			stockMovement.setType(StockMovementType.PRODUCAO_ENTRADA);
			stockMovement.setProduct(new ProductDTO(entity.getProduct()));
			stockMovement=stockMovementService.insert(stockMovement);
			entity.setStockId(stockMovement.getId());

			entity=repository.save(entity);
			return new ProductionOrderProducedDTO(entity);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Entity not found");
		}catch (DataIntegrityViolationException e ) {
			throw new DatabaseException("Database integrity reference");
		}catch(DataException e) {
			throw new DatabaseException("Database integrity reference");
		}catch(Exception e) {
			throw new UntreatedException(e.getMessage());
		}
		
	}
	@Transactional
	public ProductionOrderProducedDTO update(ProductionOrderProducedDTO dto) {
		try {
			ProductionOrderProduced entity = new ProductionOrderProduced();
			StockMovement stockMovement = new StockMovement();
			ProductionOrder productionOrder = productionOrderRepository.getOne(dto.getProductionOrderId());
			
			if (productionOrder.getStatus() != ProductionOrderStatus.ABERTO) {
				throw new ValidationException("Produ????o com status " + productionOrder.getStatus() + " n??o pode ser alterada");
			}
			
			ProductionOrderProducedPK pk = new ProductionOrderProducedPK();  
			pk.setProductionOrder(productionOrder);
			pk.setPallet(dto.getPallet());
			
			entity = repository.getOne(pk);
			convertToEntityUpdate(dto, entity);
			entity=repository.save(entity);
			
			/*Inserir produ????o na movimenta????o de estoque*/
			stockMovement = stockMovementRepository.getOne(entity.getStockId());
			stockMovement.setEntry(entity.getQuantity());
			stockMovement.setProduct(entity.getProduct());
			stockMovement.setCost(entity.getUnitCost());
			stockMovementService.update(stockMovement.getId(), new StockMovementDTO(stockMovement));
			
			return new ProductionOrderProducedDTO(entity);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Entity not found");
		}catch (DataIntegrityViolationException e ) {
			throw new DatabaseException("Database integrity reference");
		}catch(DataException e) {
			throw new DatabaseException("Database integrity reference");
		}catch(Exception e) {
			throw new UntreatedException(e.getMessage());
		}	
	}
	
	@Transactional
	public ProductionOrderProducedDTO updateService(ProductionOrderProducedDTO dto) {
		try {
			ProductionOrderProduced entity = new ProductionOrderProduced();
			StockMovement stockMovement = new StockMovement();
			ProductionOrder productionOrder = productionOrderRepository.getOne(dto.getProductionOrderId());
						
			
			ProductionOrderProducedPK pk = new ProductionOrderProducedPK();  
			pk.setProductionOrder(productionOrder);
			pk.setPallet(dto.getPallet());
			
			entity = repository.getOne(pk);
			convertToEntityUpdate(dto, entity);
			entity=repository.save(entity);
			
			/*Atualizar produ????o na movimenta????o de estoque*/
			stockMovement = stockMovementRepository.getOne(entity.getStockId());
			stockMovement.setEntry(entity.getQuantity());
			stockMovement.setProduct(entity.getProduct());
			
			logger.info("Produced Product: " + entity.getProduct().getName() + " Cost: "+ entity.getUnitCost());
			
			stockMovement.setCost(entity.getUnitCost());
			stockMovementService.update(stockMovement.getId(), new StockMovementDTO(stockMovement));
			
			return new ProductionOrderProducedDTO(entity);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Entity not found");
		}catch (DataIntegrityViolationException e ) {
			throw new DatabaseException("Database integrity reference");
		}catch(DataException e) {
			throw new DatabaseException("Database integrity reference");
		}catch(Exception e) {
			throw new UntreatedException(e.getMessage());
		}	
	}
	
	@Transactional
	public void delete(Long productionOrderId, Integer pallet) {
		try {
			ProductionOrderProduced entity = new ProductionOrderProduced();
			
			ProductionOrder productionOrder = productionOrderRepository.getOne(productionOrderId);
			if (productionOrder.getStatus() != ProductionOrderStatus.ABERTO) {
				throw new ValidationException("Produ????o com status " + productionOrder.getStatus() + " n??o pode ser alterada");
			}
			ProductionOrderProducedPK pk = new ProductionOrderProducedPK();  
			pk.setProductionOrder(productionOrder);
			pk.setPallet(pallet);
			entity=repository.getOne(pk);
			
			stockMovementService.delete(entity.getStockId());
			
			repository.deleteById(pk);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Entity not found");
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found: " + productionOrderId);
		}catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}catch (Exception e) {
			throw new UntreatedException(e.getMessage());
		}
	}

	public void convertToEntity(ProductionOrderProducedDTO dto,ProductionOrderProduced entity) {
			entity.setProductionOrder(productionOrderRepository.getOne(dto.getProductionOrderId()));
			entity.setProduct(productRepository.getOne(dto.getProduct().getId()));
			entity.setPallet(dto.getPallet());
			entity.setPalletStatus(palletStatusRepository.getOne(dto.getPalletStatus().getId()));
			entity.setQuantity(dto.getQuantity());
			entity.setManufacturingDate(dto.getManufacturingDate());
			entity.setLote(dto.getLote());
			entity.setUnitCost(dto.getUnitCost());
	}
	public void convertToEntityUpdate(ProductionOrderProducedDTO dto,ProductionOrderProduced entity) {
		entity.setProduct(productRepository.getOne(dto.getProduct().getId()));
		entity.setPalletStatus(palletStatusRepository.getOne(dto.getPalletStatus().getId()));
		entity.setQuantity(dto.getQuantity());
		entity.setManufacturingDate(dto.getManufacturingDate());
		entity.setLote(dto.getLote());
		entity.setUnitCost(dto.getUnitCost());
}
	
}
