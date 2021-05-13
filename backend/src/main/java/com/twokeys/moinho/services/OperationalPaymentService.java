package com.twokeys.moinho.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twokeys.moinho.dto.OperationalPaymentDTO;
import com.twokeys.moinho.entities.OperationalPayment;
import com.twokeys.moinho.repositories.OperationalCostTypeRepository;
import com.twokeys.moinho.repositories.OperationalPaymentRepository;
import com.twokeys.moinho.services.exceptions.DatabaseException;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;



@Service
public class OperationalPaymentService {
	@Autowired
	private OperationalPaymentRepository repository;
	@Autowired
	private OperationalCostTypeRepository apportionmentRepository;
	
	@Transactional(readOnly=true)
	public OperationalPaymentDTO findById(Long id){
		Optional<OperationalPayment> obj = repository.findById(id);
		OperationalPayment entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new OperationalPaymentDTO(entity);
	}
	@Transactional
	public OperationalPaymentDTO insert(OperationalPaymentDTO dto) {
		try {
		OperationalPayment entity =new OperationalPayment();
			convertToEntity(dto, entity);
			return new OperationalPaymentDTO(repository.save(entity));
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found");
		}
	}
	@Transactional
	public OperationalPaymentDTO update(Long id, OperationalPaymentDTO dto) {
		try {
			OperationalPayment entity = repository.getOne(id);
			convertToEntity(dto, entity);
			entity = repository.save(entity);
			return new OperationalPaymentDTO(entity);
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
	public void convertToEntity(OperationalPaymentDTO dto, OperationalPayment entity) {
		entity.setDate(dto.getDate());
		entity.setDescription(dto.getDescription());
		entity.setDocumentNumber(dto.getDocumentNumber());
		entity.setValue(dto.getValue());
		entity.setApportionmentType(apportionmentRepository.getOne(dto.getApportionmentType().getId()));
	}
}
