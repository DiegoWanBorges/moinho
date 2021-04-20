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

import com.twokeys.moinho.dto.ProductionOrderItemsDTO;
import com.twokeys.moinho.entities.Occurrence;
import com.twokeys.moinho.entities.Product;
import com.twokeys.moinho.entities.ProductionOrder;
import com.twokeys.moinho.entities.ProductionOrderItems;
import com.twokeys.moinho.repositories.OccurrenceRepository;
import com.twokeys.moinho.repositories.ProductRepository;
import com.twokeys.moinho.repositories.ProductionOrderItemsRepository;
import com.twokeys.moinho.repositories.ProductionOrderRepository;
import com.twokeys.moinho.services.exceptions.DatabaseException;
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
	@Autowired
	private OccurrenceRepository occurrenceRepository;
	
	@Transactional
	public List<ProductionOrderItemsDTO> insert(List<ProductionOrderItemsDTO> dto) {
		try {
			ProductionOrderItems entity = new ProductionOrderItems();
			List<ProductionOrderItems> entityList = new ArrayList<>();
			ProductionOrder order = new ProductionOrder();
			
			order.setId(dto.get(0).getProductionOrderId());
			
			
			
			Integer serie = repository.findMaxSerie(order) + 1;
		
			for(ProductionOrderItemsDTO itemDto : dto){
				entity = new ProductionOrderItems();
				itemDto.setSerie(serie);
				entity= convertToEntity(itemDto);
				entityList.add(entity);
			}
			entityList = repository.saveAll(entityList);
			dto.clear();
			
			for(ProductionOrderItems item: entityList) {
				dto.add(new ProductionOrderItemsDTO(item));
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
	
	public ProductionOrderItems convertToEntity(ProductionOrderItemsDTO dto) {
			ProductionOrderItems entity = new ProductionOrderItems(); 
			
			ProductionOrder productionOrder = productionOrderRepository.getOne(dto.getProductionOrderId());
			Product product = productRepository.getOne(dto.getProduct().getId());
			
			Occurrence occurrence = occurrenceRepository.getOne(dto.getOccurrence().getId());
			
			entity.setSerie(dto.getSerie());
			entity.setProduct(product);
			entity.setProductionOrder(productionOrder);
			entity.setQuantity(dto.getQuantity());
			entity.setCost(dto.getCost());
			entity.setType(dto.getType());
			entity.setOccurrence(occurrence);
			return entity;
	}
}
