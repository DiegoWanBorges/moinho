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

import com.twokeys.moinho.dto.OccurrenceDTO;
import com.twokeys.moinho.entities.Occurrence;
import com.twokeys.moinho.repositories.OccurrenceRepository;
import com.twokeys.moinho.services.exceptions.DatabaseException;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;



@Service
public class OccurrenceService {
	@Autowired
	private OccurrenceRepository repository;
	
	@Transactional(readOnly=true)
	public Page<OccurrenceDTO> findAllPaged(String name,PageRequest pageRequest){
	String nameConcat ="%"+name+"%";
	Page<Occurrence> list = repository.findByNameLikeIgnoreCase(nameConcat,pageRequest);
		return list.map(x -> new OccurrenceDTO(x));
	}
	
	@Transactional(readOnly=true)
	public List<OccurrenceDTO> findByNameLikeIgnoreCase(String name){
		String nameConcat = "%"+name+"%";
		
		List<Occurrence> list =  repository.findByNameLikeIgnoreCase(nameConcat);
		return list.stream().map(x -> new OccurrenceDTO(x)).collect(Collectors.toList());
		
	}
	@Transactional(readOnly=true)
	public OccurrenceDTO findById(Long id){
		Optional<Occurrence> obj = repository.findById(id);
		Occurrence entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new OccurrenceDTO(entity);
	}
	@Transactional
	public OccurrenceDTO insert(OccurrenceDTO dto) {
			Occurrence entity =new Occurrence();
			entity.setName(dto.getName());
			return new OccurrenceDTO(repository.save(entity));
	}
	@Transactional
	public OccurrenceDTO update(Long id, OccurrenceDTO dto) {
		try {
			Occurrence entity = repository.getOne(id);
			entity.setName(dto.getName());
			entity = repository.save(entity);
			return new OccurrenceDTO(entity);
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
