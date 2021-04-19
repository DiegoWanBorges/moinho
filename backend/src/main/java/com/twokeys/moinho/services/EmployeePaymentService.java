package com.twokeys.moinho.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twokeys.moinho.dto.EmployeePaymentDTO;
import com.twokeys.moinho.entities.EmployeePayment;
import com.twokeys.moinho.repositories.EmployeePaymentRepository;
import com.twokeys.moinho.repositories.EmployeePaymentTypeRepository;
import com.twokeys.moinho.repositories.EmployeeRepository;
import com.twokeys.moinho.services.exceptions.DatabaseException;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;



@Service
public class EmployeePaymentService {
	@Autowired
	private EmployeePaymentRepository repository;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private EmployeePaymentTypeRepository employeePaymentTypeRepository;
	
		
	@Transactional(readOnly=true)
	public EmployeePaymentDTO findById(Long id){
		Optional<EmployeePayment> obj = repository.findById(id);
		EmployeePayment entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new EmployeePaymentDTO(entity);
	}
	@Transactional
	public EmployeePaymentDTO insert(EmployeePaymentDTO dto) {
		try {
		EmployeePayment entity =new EmployeePayment();
			convertToEntity(dto, entity);
			return new EmployeePaymentDTO(repository.save(entity));
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found");
		}
	}
	@Transactional
	public EmployeePaymentDTO update(Long id, EmployeePaymentDTO dto) {
		try {
			EmployeePayment entity = repository.getOne(id);
			convertToEntity(dto, entity);
			entity = repository.save(entity);
			return new EmployeePaymentDTO(entity);
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
	public void convertToEntity(EmployeePaymentDTO dto, EmployeePayment entity) {
		entity.setDate(dto.getDate());
		entity.setPaymentAmount(dto.getPaymentAmount());
		entity.setEmployee(employeeRepository.getOne(dto.getEmployee().getId()));
		entity.setEmployeePaymentType(employeePaymentTypeRepository.getOne(dto.getEmployeePaymentType().getId()));
	}
}
