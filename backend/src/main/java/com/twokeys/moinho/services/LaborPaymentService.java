package com.twokeys.moinho.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twokeys.moinho.dto.LaborPaymentDTO;
import com.twokeys.moinho.entities.LaborPayment;
import com.twokeys.moinho.repositories.LaborPaymentRepository;
import com.twokeys.moinho.repositories.LaborCostTypeRepository;
import com.twokeys.moinho.repositories.EmployeeRepository;
import com.twokeys.moinho.services.exceptions.DatabaseException;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;



@Service
public class LaborPaymentService {
	@Autowired
	private LaborPaymentRepository repository;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private LaborCostTypeRepository laborCostTypeRepository;
	
		
	@Transactional(readOnly=true)
	public LaborPaymentDTO findById(Long id){
		Optional<LaborPayment> obj = repository.findById(id);
		LaborPayment entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new LaborPaymentDTO(entity);
	}
	@Transactional
	public LaborPaymentDTO insert(LaborPaymentDTO dto) {
		try {
		LaborPayment entity =new LaborPayment();
			convertToEntity(dto, entity);
			return new LaborPaymentDTO(repository.save(entity));
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found");
		}
	}
	@Transactional
	public LaborPaymentDTO update(Long id, LaborPaymentDTO dto) {
		try {
			LaborPayment entity = repository.getOne(id);
			convertToEntity(dto, entity);
			entity = repository.save(entity);
			return new LaborPaymentDTO(entity);
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
	public void convertToEntity(LaborPaymentDTO dto, LaborPayment entity) {
		entity.setDate(dto.getDate());
		entity.setDescription(dto.getDescription());
		entity.setDocumentNumber(dto.getDocumentNumber());
		entity.setValue(dto.getValue());
		entity.setEmployee(employeeRepository.getOne(dto.getEmployee().getId()));
		entity.setLaborCostType(laborCostTypeRepository.getOne(dto.getLaborCostType().getId()));
	}
}
