package com.twokeys.moinho.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twokeys.moinho.dto.EmployeeDTO;
import com.twokeys.moinho.entities.Employee;
import com.twokeys.moinho.repositories.EmployeeRepository;
import com.twokeys.moinho.repositories.SectorRepository;
import com.twokeys.moinho.services.exceptions.DatabaseException;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;



@Service
public class EmployeeService {
	protected final Log logger = LogFactory.getLog(getClass());
	@Autowired
	private EmployeeRepository repository;
	@Autowired
	private SectorRepository sectorRepository;
	
	@Transactional(readOnly=true)
	public List<EmployeeDTO> findByNameLikeIgnoreCase(String name){
		String nameConcat = "%"+name+"%";
		
		List<Employee> list =  repository.findByNameLikeIgnoreCase(nameConcat);
		return list.stream().map(x -> new EmployeeDTO(x)).collect(Collectors.toList());
		
	}
	@Transactional(readOnly=true)
	public EmployeeDTO findById(Long id){
		Optional<Employee> obj = repository.findById(id);
		Employee entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new EmployeeDTO(entity);
	}
	@Transactional
	public EmployeeDTO insert(EmployeeDTO dto) {
		try {
		Employee entity =new Employee();
			convertToEntity(dto, entity);
			return new EmployeeDTO(repository.save(entity));
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found");
		}
	}
	@Transactional
	public EmployeeDTO update(Long id, EmployeeDTO dto) {
		try {
			Employee entity = repository.getOne(id);
			convertToEntity(dto, entity);
			entity = repository.save(entity);
			return new EmployeeDTO(entity);
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
	public void convertToEntity(EmployeeDTO dto, Employee entity) {
		try {
			entity.setName(dto.getName());
			entity.setSector(sectorRepository.getOne(dto.getSector().getId()));
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found");
		}catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
}
