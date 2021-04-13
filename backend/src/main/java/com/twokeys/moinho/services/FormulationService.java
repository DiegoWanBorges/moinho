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

import com.twokeys.moinho.dto.FormulationDTO;
import com.twokeys.moinho.entities.Formulation;
import com.twokeys.moinho.repositories.FormulationRepository;
import com.twokeys.moinho.repositories.ProductRepository;
import com.twokeys.moinho.services.exceptions.DatabaseException;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;



@Service
public class FormulationService {
	@Autowired
	private FormulationRepository repository;
	
	@Autowired
	private ProductRepository productRepository;
	
	
	@Transactional(readOnly=true)
	public List<FormulationDTO> findByNameLikeIgnoreCase(String description){
		String nameConcat = "%"+description+"%";
		List<Formulation> list =  repository.findByDescriptionLikeIgnoreCase(nameConcat);
		return list.stream().map(x -> new FormulationDTO(x)).collect(Collectors.toList());
		
	}
	@Transactional(readOnly=true)
	public FormulationDTO findById(Long id){
		Optional<Formulation> obj = repository.findById(id);
		Formulation entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new FormulationDTO(entity);
	}
	@Transactional
	public FormulationDTO insert(FormulationDTO dto) {
		try {
		Formulation entity =new Formulation();
			convertToEntity(dto, entity);
			return new FormulationDTO(repository.save(entity));
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found");
		}
	}
	@Transactional
	public FormulationDTO update(Long id, FormulationDTO dto) {
		try {
			Formulation entity = repository.getOne(id);
			convertToEntity(dto, entity);
			entity = repository.save(entity);
			return new FormulationDTO(entity);
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
	public void convertToEntity(FormulationDTO dto, Formulation entity) {
		entity.setCoefficient(dto.getCoefficient());
		entity.setDescription(dto.getDescription());
		entity.setProduct(productRepository.getOne(dto.getProduct().getId()));
	}
}
