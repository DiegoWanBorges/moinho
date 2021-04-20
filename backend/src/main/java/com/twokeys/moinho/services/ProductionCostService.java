package com.twokeys.moinho.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twokeys.moinho.dto.ProductionCostDTO;
import com.twokeys.moinho.entities.ProductionCost;
import com.twokeys.moinho.repositories.ApportionmentTypeRepository;
import com.twokeys.moinho.repositories.ProductionCostRepository;
import com.twokeys.moinho.services.exceptions.DatabaseException;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;



@Service
public class ProductionCostService {
	@Autowired
	private ProductionCostRepository repository;
	@Autowired
	private ApportionmentTypeRepository apportionmentRepository;
	
	@Transactional(readOnly=true)
	public ProductionCostDTO findById(Long id){
		Optional<ProductionCost> obj = repository.findById(id);
		ProductionCost entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ProductionCostDTO(entity);
	}
	@Transactional
	public ProductionCostDTO insert(ProductionCostDTO dto) {
		try {
		ProductionCost entity =new ProductionCost();
			convertToEntity(dto, entity);
			return new ProductionCostDTO(repository.save(entity));
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found");
		}
	}
	@Transactional
	public ProductionCostDTO update(Long id, ProductionCostDTO dto) {
		try {
			ProductionCost entity = repository.getOne(id);
			convertToEntity(dto, entity);
			entity = repository.save(entity);
			return new ProductionCostDTO(entity);
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
	public void convertToEntity(ProductionCostDTO dto, ProductionCost entity) {
		entity.setDate(dto.getDate());
		entity.setDescription(dto.getDescription());
		entity.setPaymentAmount(dto.getPaymentAmount());
		entity.setApportionmentType(apportionmentRepository.getOne(dto.getApportionmentType().getId()));
	}
}
