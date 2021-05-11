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

import com.twokeys.moinho.dto.UnityDTO;
import com.twokeys.moinho.entities.Unity;
import com.twokeys.moinho.repositories.UnityRepository;
import com.twokeys.moinho.services.exceptions.DatabaseException;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;



@Service
public class UnityService {
	@Autowired
	private UnityRepository repository;
	
	@Transactional(readOnly=true)
	public List<UnityDTO> findByDescriptionLikeIgnoreCase(String description){
		String nameConcat = "%"+description+"%";
		List<Unity> list =  repository.findByDescriptionLikeIgnoreCase(nameConcat);
		return list.stream().map(x -> new UnityDTO(x)).collect(Collectors.toList());
	}
	@Transactional(readOnly=true)
	public Page<UnityDTO> findAllPaged(String description,PageRequest pageRequest){
	String nameConcat ="%"+description+"%";
	Page<Unity> list = repository.findByDescriptionLikeIgnoreCase(nameConcat,pageRequest);
		return list.map(x -> new UnityDTO(x));
		
	}
	@Transactional(readOnly=true)
	public UnityDTO findById(String id){
		Optional<Unity> obj = repository.findById(id);
		Unity entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new UnityDTO(entity);
	}
	@Transactional
	public UnityDTO insert(UnityDTO dto) {
			Unity entity =new Unity();
			entity.setId(dto.getId());
			entity.setDescription(dto.getDescription());
			return new UnityDTO(repository.save(entity));
	}
	@Transactional
	public UnityDTO update(String id, UnityDTO dto) {
		try {
			Unity entity = repository.getOne(id);
			entity.setId(dto.getId());
			entity.setDescription(dto.getDescription());
			entity = repository.save(entity);
			return new UnityDTO(entity);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		}
	}
	public void delete(String id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		}catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
		
	}
}
