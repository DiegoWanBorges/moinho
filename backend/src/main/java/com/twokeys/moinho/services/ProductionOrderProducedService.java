package com.twokeys.moinho.services;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twokeys.moinho.dto.ProductDTO;
import com.twokeys.moinho.dto.ProductionOrderProducedDTO;
import com.twokeys.moinho.dto.StockMovementDTO;
import com.twokeys.moinho.entities.ProductionOrder;
import com.twokeys.moinho.entities.ProductionOrderProduced;
import com.twokeys.moinho.entities.StockMovement;
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
	
	@Transactional
	public ProductionOrderProducedDTO insert(ProductionOrderProducedDTO dto) {
		try {
			ProductionOrderProduced entity = new ProductionOrderProduced();
			ProductionOrder order = new ProductionOrder();
			order.setId(dto.getProductionOrderId());
			
			/*Recupera o proximo pallet*/
			Integer pallet = repository.findMaxPallet(order) + 1;
			dto.setPallet(pallet);
			convertToEntity(dto, entity);

			/*Inserir produção na movimentação de estoque*/
			StockMovementDTO stockMovement = new StockMovementDTO();
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
			ProductionOrderProducedPK pk = new ProductionOrderProducedPK();  
			pk.setProductionOrder(productionOrder);
			pk.setPallet(dto.getPallet());
			
			entity = repository.getOne(pk);
			convertToEntityUpdate(dto, entity);
			entity=repository.save(entity);
			
			/*Inserir produção na movimentação de estoque*/
			stockMovement = stockMovementRepository.getOne(entity.getStockId());
			stockMovement.setEntry(entity.getQuantity());
			stockMovement.setProduct(entity.getProduct());
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
	}
	public void convertToEntityUpdate(ProductionOrderProducedDTO dto,ProductionOrderProduced entity) {
		entity.setProduct(productRepository.getOne(dto.getProduct().getId()));
		entity.setPalletStatus(palletStatusRepository.getOne(dto.getPalletStatus().getId()));
		entity.setQuantity(dto.getQuantity());
		entity.setManufacturingDate(dto.getManufacturingDate());
		entity.setLote(dto.getLote());
}
	
}
