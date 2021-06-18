package com.twokeys.moinho.services;

import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twokeys.moinho.dto.LaborPaymentDTO;
import com.twokeys.moinho.entities.Employee;
import com.twokeys.moinho.entities.LaborCostType;
import com.twokeys.moinho.entities.LaborPayment;
import com.twokeys.moinho.repositories.EmployeeRepository;
import com.twokeys.moinho.repositories.LaborCostTypeRepository;
import com.twokeys.moinho.repositories.LaborPaymentRepository;
import com.twokeys.moinho.services.exceptions.BusinessRuleException;
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
	@Autowired
	private CostCalculationService costCalculationService;
	
	@Transactional(readOnly=true)
	public Page<LaborPaymentDTO> findByDateAndEmployeeAndLaborCostType(Long employeeId,Long laborCostTypeId,LocalDate startDate,LocalDate endDate,PageRequest pageRequest){
		Employee employee = (employeeId==0) ? null : employeeRepository.getOne(employeeId);
		LaborCostType laborCostType = (laborCostTypeId==0) ? null : laborCostTypeRepository.getOne(laborCostTypeId);
		Page<LaborPayment> page = repository.findByDateAndEmployeeAndLaborCostType(employee,laborCostType,startDate,endDate,pageRequest);
		return page.map(x -> new LaborPaymentDTO(x));
	}
	
	@Transactional(readOnly=true)
	public LaborPaymentDTO findById(Long id){
		Optional<LaborPayment> obj = repository.findById(id);
		LaborPayment entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new LaborPaymentDTO(entity);
	}
	@Transactional
	public LaborPaymentDTO insert(LaborPaymentDTO dto) {
		try {
			if(costCalculationService.hasCostCalculation(dto.getDate().getYear(),dto.getDate().getMonth().getValue())) {
				throw new BusinessRuleException("Operação não permitida. Apuração de custo finalizada!");
			}
			LaborPayment entity =new LaborPayment();
			convertToEntity(dto, entity);
			return new LaborPaymentDTO(repository.save(entity));
		}catch(BusinessRuleException e) {
			throw new BusinessRuleException(e.getMessage());
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found");
		}
	}
	@Transactional
	public LaborPaymentDTO update(Long id, LaborPaymentDTO dto) {
		try {
			LaborPayment entity = repository.getOne(id);
			if(costCalculationService.hasCostCalculation(entity.getDate().getYear(),entity.getDate().getMonth().getValue())) {
				throw new BusinessRuleException("Operação não permitida. Apuração de custo finalizada!");
			}
			convertToEntity(dto, entity);
			entity = repository.save(entity);
			return new LaborPaymentDTO(entity);
		}catch(BusinessRuleException e) {
			throw new BusinessRuleException(e.getMessage());
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		}
	}
	@Transactional
	public void delete(Long id) {
		try {
			 LaborPayment entity = repository.getOne(id);
			 if(costCalculationService.hasCostCalculation(entity.getDate().getYear(),entity.getDate().getMonth().getValue())) {
				throw new BusinessRuleException("Operação não permitida. Apuração de custo finalizada!");
			 }
			 repository.deleteById(id);
		}catch(BusinessRuleException e) {
			throw new BusinessRuleException(e.getMessage());
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
