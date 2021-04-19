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

import com.twokeys.moinho.dto.EmployeePaymentTypeDTO;
import com.twokeys.moinho.entities.EmployeePaymentType;
import com.twokeys.moinho.repositories.EmployeePaymentTypeRepository;
import com.twokeys.moinho.services.exceptions.DatabaseException;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;



@Service
public class EmployeePaymentTypeService {
	@Autowired
	private EmployeePaymentTypeRepository repository;
	
	@Transactional(readOnly=true)
	public List<EmployeePaymentTypeDTO> findByNameLikeIgnoreCase(String name){
		String nameConcat = "%"+name+"%";
		
		List<EmployeePaymentType> list =  repository.findByNameLikeIgnoreCase(nameConcat);
		return list.stream().map(x -> new EmployeePaymentTypeDTO(x)).collect(Collectors.toList());
		
	}
	@Transactional(readOnly=true)
	public EmployeePaymentTypeDTO findById(Long id){
		Optional<EmployeePaymentType> obj = repository.findById(id);
		EmployeePaymentType entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new EmployeePaymentTypeDTO(entity);
	}
	@Transactional
	public EmployeePaymentTypeDTO insert(EmployeePaymentTypeDTO dto) {
			EmployeePaymentType entity =new EmployeePaymentType();
			entity.setName(dto.getName());
			return new EmployeePaymentTypeDTO(repository.save(entity));
	}
	@Transactional
	public EmployeePaymentTypeDTO update(Long id, EmployeePaymentTypeDTO dto) {
		try {
			EmployeePaymentType entity = repository.getOne(id);
			entity.setName(dto.getName());
			entity = repository.save(entity);
			return new EmployeePaymentTypeDTO(entity);
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
