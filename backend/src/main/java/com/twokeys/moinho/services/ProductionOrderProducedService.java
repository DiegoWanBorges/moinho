package com.twokeys.moinho.services;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twokeys.moinho.dto.ProductDTO;
import com.twokeys.moinho.dto.ProductionOrderProducedDTO;
import com.twokeys.moinho.dto.StockMovementDTO;
import com.twokeys.moinho.entities.ProductionOrder;
import com.twokeys.moinho.entities.ProductionOrderProduced;
import com.twokeys.moinho.entities.enums.StockMovementType;
import com.twokeys.moinho.repositories.ProducedProductStatusRepository;
import com.twokeys.moinho.repositories.ProductRepository;
import com.twokeys.moinho.repositories.ProductionOrderProducedRepository;
import com.twokeys.moinho.repositories.ProductionOrderRepository;
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
	private ProducedProductStatusRepository producedProductStatusRepository;
	@Autowired
	private StockMovementService stockMovementService;	
	
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
			throw new UntreatedException("untreated exception: " + e.getMessage());
		}
		
	}
	public void convertToEntity(ProductionOrderProducedDTO dto,ProductionOrderProduced entity) {
			entity.setProductionOrder(productionOrderRepository.getOne(dto.getProductionOrderId()));
			entity.setProduct(productRepository.getOne(dto.getProduct().getId()));
			entity.setPallet(dto.getPallet());
			entity.setProducedProductStatus(producedProductStatusRepository.getOne(dto.getProducedProductStatus().getId()));
			entity.setQuantity(dto.getQuantity());
			entity.setManufacturingDate(dto.getManufacturingDate());
			entity.setLote(dto.getLote());
	}
}
