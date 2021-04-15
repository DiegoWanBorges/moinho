package com.twokeys.moinho.services;

import java.time.Instant;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twokeys.moinho.dto.FormulationDTO;
import com.twokeys.moinho.dto.FormulationItemsDTO;
import com.twokeys.moinho.dto.ProductionOrderDTO;
import com.twokeys.moinho.dto.ProductionOrderItemsDTO;
import com.twokeys.moinho.entities.ProductionOrder;
import com.twokeys.moinho.entities.enums.ProductionOrderItemsType;
import com.twokeys.moinho.entities.enums.ProductionOrderStatus;
import com.twokeys.moinho.repositories.FormulationRepository;
import com.twokeys.moinho.repositories.ProductionOrderRepository;
import com.twokeys.moinho.services.exceptions.DatabaseException;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;



@Service
public class ProductionOrderService {
	@Autowired
	private ProductionOrderRepository repository;
	
	@Autowired
	private FormulationRepository formulationRepository;
	
	@Autowired
	private FormulationService formulationService;
	
	@Transactional(readOnly=true)
	public ProductionOrderDTO createProductionOrder(Long formulationId, Double ammount){
		FormulationDTO formulation = formulationService.findById(formulationId);
		ProductionOrderDTO productionOrder = new ProductionOrderDTO();
		ProductionOrderItemsDTO productionOrderItems; 
		Double quantityItem =0.0;
	
		for (FormulationItemsDTO formulationItems : formulation.getFormulationItems()) {
			quantityItem= (ammount*formulationItems.getQuantity())/formulation.getCoefficient();
			productionOrderItems = new ProductionOrderItemsDTO();
			productionOrderItems.setSerie(1);
			productionOrderItems.setCost(0.0);
			productionOrderItems.setQuantity(quantityItem);
			productionOrderItems.setType(ProductionOrderItemsType.NORMAL);
			productionOrderItems.setProduct(formulationItems.getProduct());
			productionOrder.getProductionOrderItems().add(productionOrderItems);
		}
		productionOrder.setEmission(Instant.now());
		productionOrder.setExpectedAmount(ammount);
		productionOrder.setStatus(ProductionOrderStatus.ABERTO);
		productionOrder.setFormulation(formulation);
		return productionOrder;
		
	}
	
	@Transactional(readOnly=true)
	public ProductionOrderDTO findById(Long id){
		Optional<ProductionOrder> obj = repository.findById(id);
		ProductionOrder entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ProductionOrderDTO(entity);
	}
	@Transactional
	public ProductionOrderDTO insert(ProductionOrderDTO dto) {
		try {
		ProductionOrder entity =new ProductionOrder();
			convertToEntity(dto, entity);
			return new ProductionOrderDTO(repository.save(entity));
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found");
		}
	}
	@Transactional
	public ProductionOrderDTO update(Long id, ProductionOrderDTO dto) {
		try {
			ProductionOrder entity = repository.getOne(id);
			convertToEntity(dto, entity);
			entity = repository.save(entity);
			return new ProductionOrderDTO(entity);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		}
	}
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		}catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
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
