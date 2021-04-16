package com.twokeys.moinho.services;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twokeys.moinho.dto.ProductionOrderItemsDTO;
import com.twokeys.moinho.entities.Product;
import com.twokeys.moinho.entities.ProductionOrder;
import com.twokeys.moinho.entities.ProductionOrderItems;
import com.twokeys.moinho.repositories.ProductRepository;
import com.twokeys.moinho.repositories.ProductionOrderItemsRepository;
import com.twokeys.moinho.repositories.ProductionOrderRepository;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;



@Service
public class ProductionOrderItemsService {
	protected final Log logger = LogFactory.getLog(getClass());
	@Autowired
	private ProductionOrderItemsRepository repository;
	@Autowired 
	private ProductRepository productRepository; 
	@Autowired
	private ProductionOrderRepository productionOrderRepository;
	
	
	@Transactional
	public ProductionOrderItemsDTO insert(ProductionOrderItemsDTO dto) {
		try {
			ProductionOrderItems entity = new ProductionOrderItems();
			convertToEntity(dto, entity);
			return new ProductionOrderItemsDTO(repository.save(entity));
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found");
		}
	}
	public void convertToEntity(ProductionOrderItemsDTO dto, ProductionOrderItems entity) {
		ProductionOrder productionOrder = productionOrderRepository.getOne(dto.getProductionOrderId());
		Product product = productRepository.getOne(dto.getProduct().getId());
		
		entity.setSerie(dto.getSerie());
		entity.setProduct(product);
		entity.setProductionOrder(productionOrder);
		entity.setQuantity(dto.getQuantity());
		entity.setCost(dto.getCost());
		entity.setType(dto.getType());
	}
}
