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

import com.twokeys.moinho.dto.LineDTO;
import com.twokeys.moinho.entities.Line;
import com.twokeys.moinho.repositories.LineRepository;
import com.twokeys.moinho.services.exceptions.DatabaseException;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;



@Service
public class LineService {
	@Autowired
	private LineRepository repository;
	
	@Transactional(readOnly=true)
	public List<LineDTO> findByNameLikeIgnoreCase(String name){
		String nameConcat = "%"+name+"%";
		
		List<Line> list =  repository.findByNameLikeIgnoreCase(nameConcat);
		return list.stream().map(x -> new LineDTO(x)).collect(Collectors.toList());
		
	}
	@Transactional(readOnly=true)
	public LineDTO findById(Long id){
		Optional<Line> obj = repository.findById(id);
		Line entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new LineDTO(entity);
	}
	@Transactional
	public LineDTO insert(LineDTO dto) {
			Line entity =new Line();
			entity.setName(dto.getName());
			return new LineDTO(repository.save(entity));
	}
	@Transactional
	public LineDTO update(Long id, LineDTO dto) {
		try {
			Line entity = repository.getOne(id);
			entity.setName(dto.getName());
			entity = repository.save(entity);
			return new LineDTO(entity);
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
