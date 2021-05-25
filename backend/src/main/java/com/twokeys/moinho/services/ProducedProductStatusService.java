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

import com.twokeys.moinho.dto.ProducedProductStatusDTO;
import com.twokeys.moinho.entities.ProducedProductStatus;
import com.twokeys.moinho.repositories.ProducedProductStatusRepository;
import com.twokeys.moinho.services.exceptions.DatabaseException;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;



@Service
public class ProducedProductStatusService {
	@Autowired
	private ProducedProductStatusRepository repository;
	
	@Transactional(readOnly=true)
	public Page<ProducedProductStatusDTO> findAllPaged(String name,PageRequest pageRequest){
	String nameConcat ="%"+name+"%";
	Page<ProducedProductStatus> list = repository.findByNameLikeIgnoreCase(nameConcat,pageRequest);
		return list.map(x -> new ProducedProductStatusDTO(x));
	}
	
	@Transactional(readOnly=true)
	public List<ProducedProductStatusDTO> findByNameLikeIgnoreCase(String name){
		String nameConcat = "%"+name+"%";
		
		List<ProducedProductStatus> list =  repository.findByNameLikeIgnoreCase(nameConcat);
		return list.stream().map(x -> new ProducedProductStatusDTO(x)).collect(Collectors.toList());
		
	}
	@Transactional(readOnly=true)
	public ProducedProductStatusDTO findById(Long id){
		Optional<ProducedProductStatus> obj = repository.findById(id);
		ProducedProductStatus entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ProducedProductStatusDTO(entity);
	}
	@Transactional
	public ProducedProductStatusDTO insert(ProducedProductStatusDTO dto) {
			ProducedProductStatus entity =new ProducedProductStatus();
			entity.setName(dto.getName());
			return new ProducedProductStatusDTO(repository.save(entity));
	}
	@Transactional
	public ProducedProductStatusDTO update(Long id, ProducedProductStatusDTO dto) {
		try {
			ProducedProductStatus entity = repository.getOne(id);
			entity.setName(dto.getName());
			entity = repository.save(entity);
			return new ProducedProductStatusDTO(entity);
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
