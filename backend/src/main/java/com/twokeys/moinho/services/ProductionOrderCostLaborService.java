package com.twokeys.moinho.services;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twokeys.moinho.dto.ProductionOrderCostLaborDTO;
import com.twokeys.moinho.entities.ProductionOrder;
import com.twokeys.moinho.entities.ProductionOrderCostLabor;
import com.twokeys.moinho.repositories.ProductionOrderCostLaborRepository;
import com.twokeys.moinho.repositories.ProductionOrderRepository;
import com.twokeys.moinho.repositories.SectorRepository;
import com.twokeys.moinho.services.exceptions.DatabaseException;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;



@Service
public class ProductionOrderCostLaborService {
	protected final Log logger = LogFactory.getLog(getClass());
	@Autowired
	private ProductionOrderCostLaborRepository repository;
	@Autowired 
	private SectorRepository sectorRepository; 
	@Autowired
	private ProductionOrderRepository productionOrderRepository;
	
	
	@Transactional
	public ProductionOrderCostLaborDTO insert(ProductionOrderCostLaborDTO dto) {
		try {
			ProductionOrderCostLabor entity =new ProductionOrderCostLabor();
			convertToEntity(dto, entity);
			return new ProductionOrderCostLaborDTO(repository.save(entity));
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
	public void convertToEntity(ProductionOrderCostLaborDTO dto, ProductionOrderCostLabor entity) {
		entity.setProductionOrder(productionOrderRepository.getOne(dto.getProductionOrderId()));
		entity.setSector(sectorRepository.getOne(dto.getSector().getId()));
		entity.setValue(dto.getValue());
	}
}
