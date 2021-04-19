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

import com.twokeys.moinho.dto.SectorDTO;
import com.twokeys.moinho.entities.Sector;
import com.twokeys.moinho.repositories.SectorRepository;
import com.twokeys.moinho.services.exceptions.DatabaseException;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;



@Service
public class SectorService {
	@Autowired
	private SectorRepository repository;
	
	@Transactional(readOnly=true)
	public List<SectorDTO> findByNameLikeIgnoreCase(String name){
		String nameConcat = "%"+name+"%";
		
		List<Sector> list =  repository.findByNameLikeIgnoreCase(nameConcat);
		return list.stream().map(x -> new SectorDTO(x)).collect(Collectors.toList());
		
	}
	@Transactional(readOnly=true)
	public SectorDTO findById(Long id){
		Optional<Sector> obj = repository.findById(id);
		Sector entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new SectorDTO(entity);
	}
	@Transactional
	public SectorDTO insert(SectorDTO dto) {
			Sector entity =new Sector();
			entity.setName(dto.getName());
			return new SectorDTO(repository.save(entity));
	}
	@Transactional
	public SectorDTO update(Long id, SectorDTO dto) {
		try {
			Sector entity = repository.getOne(id);
			entity.setName(dto.getName());
			entity = repository.save(entity);
			return new SectorDTO(entity);
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
