package com.twokeys.moinho.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twokeys.moinho.dto.StockMovementDTO;
import com.twokeys.moinho.entities.Product;
import com.twokeys.moinho.entities.StockMovement;
import com.twokeys.moinho.repositories.ProductRepository;
import com.twokeys.moinho.repositories.StockMovementRepository;
import com.twokeys.moinho.services.exceptions.DatabaseException;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;



@Service
public class StockMovementService {
	@Autowired
	private StockMovementRepository repository;
	@Autowired
	private ProductRepository productRepository;
	
	@Transactional(readOnly=true)
	public List<StockMovementDTO> findByProduct(Long idProduct){
		Product product = new Product();
		product.setId(idProduct);
		List<StockMovement> list =  repository.findByProduct(product);
		return list.stream().map(x -> new StockMovementDTO(x)).collect(Collectors.toList());
		
	}
	@Transactional(readOnly=true)
	public StockMovementDTO findById(Long id){
		Optional<StockMovement> obj = repository.findById(id);
		StockMovement entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new StockMovementDTO(entity);
	}
	@Transactional
	public StockMovementDTO insert(StockMovementDTO dto) {
		try {
		StockMovement entity =new StockMovement();
			convertToEntity(dto, entity);
			return new StockMovementDTO(repository.save(entity));
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found");
		}
	}
	@Transactional
	public StockMovementDTO update(Long id, StockMovementDTO dto) {
		try {
			StockMovement entity = repository.getOne(id);
			convertToEntity(dto, entity);
			entity = repository.save(entity);
			return new StockMovementDTO(entity);
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
	public void convertToEntity(StockMovementDTO dto, StockMovement entity) {
		entity.setCost(dto.getCost());
		entity.setDescription(dto.getDescription());
		entity.setDate(Instant.now());
		entity.setIdOrignMovement(dto.getIdOrignMovement());
		entity.setEntry(dto.getEntry());
		entity.setOut(dto.getOut());
		entity.setProduct(productRepository.getOne(dto.getProduct().getId()));
	}
}
