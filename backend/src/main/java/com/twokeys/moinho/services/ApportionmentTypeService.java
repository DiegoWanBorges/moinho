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

import com.twokeys.moinho.dto.ApportionmentTypeDTO;
import com.twokeys.moinho.entities.ApportionmentType;
import com.twokeys.moinho.repositories.ApportionmentTypeRepository;
import com.twokeys.moinho.services.exceptions.DatabaseException;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;



@Service
public class ApportionmentTypeService {
	@Autowired
	private ApportionmentTypeRepository repository;
	
	@Transactional(readOnly=true)
	public List<ApportionmentTypeDTO> findByNameLikeIgnoreCase(String name){
		String nameConcat = "%"+name+"%";
		List<ApportionmentType> list =  repository.findByNameLikeIgnoreCase(nameConcat);
		return list.stream().map(x -> new ApportionmentTypeDTO(x)).collect(Collectors.toList());
	}
	@Transactional(readOnly=true)
	public Page<ApportionmentTypeDTO> findAllPaged(String name,PageRequest pageRequest){
	String nameConcat ="%"+name+"%";
	Page<ApportionmentType> list = repository.findByNameLikeIgnoreCase(nameConcat,pageRequest);
		return list.map(x -> new ApportionmentTypeDTO(x));
	}
	
	@Transactional(readOnly=true)
	public ApportionmentTypeDTO findById(Long id){
		Optional<ApportionmentType> obj = repository.findById(id);
		ApportionmentType entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ApportionmentTypeDTO(entity);
	}
	@Transactional
	public ApportionmentTypeDTO insert(ApportionmentTypeDTO dto) {
		try {
		ApportionmentType entity =new ApportionmentType();
			convertToEntity(dto, entity);
			return new ApportionmentTypeDTO(repository.save(entity));
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found");
		}
	}
	@Transactional
	public ApportionmentTypeDTO update(Long id, ApportionmentTypeDTO dto) {
		try {
			ApportionmentType entity = repository.getOne(id);
			convertToEntity(dto, entity);
			entity = repository.save(entity);
			return new ApportionmentTypeDTO(entity);
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
	public void convertToEntity(ApportionmentTypeDTO dto, ApportionmentType entity) {
		entity.setName(dto.getName());
		entity.setType(dto.getType());
	}
}
