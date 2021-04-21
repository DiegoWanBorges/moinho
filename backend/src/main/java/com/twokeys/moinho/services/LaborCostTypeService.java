package com.twokeys.moinho.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twokeys.moinho.dto.LaborCostTypeDTO;
import com.twokeys.moinho.entities.LaborCostType;
import com.twokeys.moinho.repositories.LaborCostTypeRepository;
import com.twokeys.moinho.services.exceptions.DatabaseException;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;



@Service
public class LaborCostTypeService {
	@Autowired
	private LaborCostTypeRepository repository;
	
	@Transactional(readOnly=true)
	public List<LaborCostTypeDTO> findByNameLikeIgnoreCase(String name){
		String nameConcat = "%"+name+"%";
		
		List<LaborCostType> list =  repository.findByNameLikeIgnoreCase(nameConcat);
		return list.stream().map(x -> new LaborCostTypeDTO(x)).collect(Collectors.toList());
		
	}
	@Transactional(readOnly=true)
	public LaborCostTypeDTO findById(Long id){
		Optional<LaborCostType> obj = repository.findById(id);
		LaborCostType entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new LaborCostTypeDTO(entity);
	}
	@Transactional
	public LaborCostTypeDTO insert(LaborCostTypeDTO dto) {
			LaborCostType entity =new LaborCostType();
			entity.setName(dto.getName());
			return new LaborCostTypeDTO(repository.save(entity));
	}
	@Transactional
	public LaborCostTypeDTO update(Long id, LaborCostTypeDTO dto) {
		try {
			LaborCostType entity = repository.getOne(id);
			entity.setName(dto.getName());
			entity = repository.save(entity);
			return new LaborCostTypeDTO(entity);
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
}
