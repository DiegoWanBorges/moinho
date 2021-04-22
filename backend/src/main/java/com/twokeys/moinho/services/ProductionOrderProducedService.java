package com.twokeys.moinho.services;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twokeys.moinho.dto.ProductionOrderProducedDTO;
import com.twokeys.moinho.entities.ProducedProductStatus;
import com.twokeys.moinho.entities.Product;
import com.twokeys.moinho.entities.ProductionOrder;
import com.twokeys.moinho.entities.ProductionOrderProduced;
import com.twokeys.moinho.repositories.ProducedProductStatusRepository;
import com.twokeys.moinho.repositories.ProductRepository;
import com.twokeys.moinho.repositories.ProductionOrderProducedRepository;
import com.twokeys.moinho.repositories.ProductionOrderRepository;
import com.twokeys.moinho.services.exceptions.DatabaseException;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;
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
		
	
	@Transactional
	public ProductionOrderProducedDTO insert(ProductionOrderProducedDTO dto) {
		try {
			ProductionOrderProduced entity = new ProductionOrderProduced();
			ProductionOrder order = new ProductionOrder();
			
			order.setId(dto.getProductionOrderId());
			Integer pallet = repository.findMaxPallet(order) + 1;
			dto.setPallet(pallet);
			
			convertToEntity(dto, entity);
			return new ProductionOrderProducedDTO(repository.save(entity));
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found");
		}catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Id not found");
		}catch(ConstraintViolationException e) {
			throw new DatabaseException("Id not found");
		}
		
	}
	
	public void convertToEntity(ProductionOrderProducedDTO dto,ProductionOrderProduced entity) {
			ProductionOrder productionOrder = productionOrderRepository.getOne(dto.getProductionOrderId());
			Product product = productRepository.getOne(dto.getProduct().getId());
			ProducedProductStatus producedProductStatus=producedProductStatusRepository.getOne(dto.getProducedProductStatus().getId()); 
			
			entity.setProductionOrder(productionOrder);
			entity.setProduct(product);
			entity.setPallet(dto.getPallet());
			entity.setProducedProductStatus(producedProductStatus);
			entity.setQuantity(dto.getQuantity());
			entity.setManufacturingDate(dto.getManufacturingDate());
			entity.setLote(dto.getLote());
	}
}
