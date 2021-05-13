package com.twokeys.moinho.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twokeys.moinho.dto.OperationalCostTypeDTO;
import com.twokeys.moinho.entities.OperationalCostType;
import com.twokeys.moinho.repositories.OperationalCostTypeRepository;
import com.twokeys.moinho.services.exceptions.DatabaseException;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;



@Service
public class OperationalCostTypeService {
	@Autowired
	private OperationalCostTypeRepository repository;
	
	@Transactional(readOnly=true)
	public List<OperationalCostTypeDTO> findByNameLikeIgnoreCase(String name){
		String nameConcat = "%"+name+"%";
		List<OperationalCostType> list =  repository.findByNameLikeIgnoreCase(nameConcat);
		return list.stream().map(x -> new OperationalCostTypeDTO(x)).collect(Collectors.toList());
	}
	@Transactional(readOnly=true)
	public Page<OperationalCostTypeDTO> findAllPaged(String name,PageRequest pageRequest){
	String nameConcat ="%"+name+"%";
	Page<OperationalCostType> list = repository.findByNameLikeIgnoreCase(nameConcat,pageRequest);
		return list.map(x -> new OperationalCostTypeDTO(x));
	}
	
	@Transactional(readOnly=true)
	public OperationalCostTypeDTO findById(Long id){
		Optional<OperationalCostType> obj = repository.findById(id);
		OperationalCostType entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new OperationalCostTypeDTO(entity);
	}
	@Transactional
	public OperationalCostTypeDTO insert(OperationalCostTypeDTO dto) {
		try {
		OperationalCostType entity =new OperationalCostType();
			convertToEntity(dto, entity);
			return new OperationalCostTypeDTO(repository.save(entity));
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found");
		}
	}
	@Transactional
	public OperationalCostTypeDTO update(Long id, OperationalCostTypeDTO dto) {
		try {
			OperationalCostType entity = repository.getOne(id);
			convertToEntity(dto, entity);
			entity = repository.save(entity);
			return new OperationalCostTypeDTO(entity);
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
	public void convertToEntity(OperationalCostTypeDTO dto, OperationalCostType entity) {
		entity.setName(dto.getName());
		entity.setType(dto.getType());
	}
}
