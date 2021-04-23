package com.twokeys.moinho.services;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twokeys.moinho.dto.ProductionOrderOperationalCostDTO;
import com.twokeys.moinho.entities.ProductionOrder;
import com.twokeys.moinho.entities.ProductionOrderOperationalCost;
import com.twokeys.moinho.repositories.ApportionmentTypeRepository;
import com.twokeys.moinho.repositories.ProductionOrderOperationalCostRepository;
import com.twokeys.moinho.repositories.ProductionOrderRepository;
import com.twokeys.moinho.services.exceptions.DatabaseException;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;



@Service
public class ProductionOrderOperationalCostService {
	protected final Log logger = LogFactory.getLog(getClass());
	@Autowired
	private ProductionOrderOperationalCostRepository repository;
	@Autowired 
	private ApportionmentTypeRepository apportionmentTypeRepository; 
	@Autowired
	private ProductionOrderRepository productionOrderRepository;
	
	
	@Transactional
	public ProductionOrderOperationalCostDTO insert(ProductionOrderOperationalCostDTO dto) {
		try {
			ProductionOrderOperationalCost entity =new ProductionOrderOperationalCost();
			convertToEntity(dto, entity);
			return new ProductionOrderOperationalCostDTO(repository.save(entity));
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found");
		}
	}
	@Transactional
	public void delete(Long ProductionOrderId) {
		try {
			ProductionOrder productionOrder = productionOrderRepository.getOne(ProductionOrderId);
			repository.deleteByIdProductionOrder(productionOrder);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("ProductionOrder id:" + ProductionOrderId + " Not found");
		}catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	public void convertToEntity(ProductionOrderOperationalCostDTO dto, ProductionOrderOperationalCost entity) {
		entity.setProductionOrder(productionOrderRepository.getOne(dto.getProductionOrderId()));
		entity.setApportionmentType(apportionmentTypeRepository.getOne(dto.getApportionmentType().getId()));
		entity.setValue(dto.getValue());
	}
}
