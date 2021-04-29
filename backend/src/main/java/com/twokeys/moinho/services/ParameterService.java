package com.twokeys.moinho.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twokeys.moinho.dto.ParameterDTO;
import com.twokeys.moinho.entities.Parameter;
import com.twokeys.moinho.repositories.ParameterRepository;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;



@Service
public class ParameterService {
	@Autowired
	private ParameterRepository repository;
	
	
	@Transactional(readOnly=true)
	public ParameterDTO findById(Long id){
		Optional<Parameter> obj = repository.findById(id);
		Parameter entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ParameterDTO(entity);
	}
	
	@Transactional
	public ParameterDTO update(Long id, ParameterDTO dto) {
		try {
			Parameter entity = repository.getOne(id);
			convertToEntity(dto, entity);
			entity = repository.save(entity);
			return new ParameterDTO(entity);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		}
	}
	public void convertToEntity(ParameterDTO dto, Parameter entity) {
		entity.setCompanyName(dto.getCompanyName());
		entity.setProductionOrderWithoutStock(dto.isProductionOrderWithoutStock());
		entity.setTypeCostUsed(dto.getTypeCostUsed());
		
	}
	
}
