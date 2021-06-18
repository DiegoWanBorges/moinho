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

import com.twokeys.moinho.dto.OperationalPaymentDTO;
import com.twokeys.moinho.entities.OperationalCostType;
import com.twokeys.moinho.entities.OperationalPayment;
import com.twokeys.moinho.repositories.OperationalCostTypeRepository;
import com.twokeys.moinho.repositories.OperationalPaymentRepository;
import com.twokeys.moinho.services.exceptions.BusinessRuleException;
import com.twokeys.moinho.services.exceptions.DatabaseException;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;



@Service
public class OperationalPaymentService {
	@Autowired
	private OperationalPaymentRepository repository;
	@Autowired
	private OperationalCostTypeRepository operationalCostTypeRepository;
	@Autowired
	private CostCalculationService costCalculationService;
	@Transactional(readOnly=true)
	public Page<OperationalPaymentDTO> findByDateAndType(Long operationalCostId,LocalDate startDate,LocalDate endDate,PageRequest pageRequest){
		OperationalCostType operationalCostType = (operationalCostId==0) ? null : operationalCostTypeRepository.getOne(operationalCostId);
		Page<OperationalPayment> page = repository.findByDateAndType(operationalCostType,startDate,endDate,pageRequest);
		return page.map(x -> new OperationalPaymentDTO(x));
	}
	
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
			if(costCalculationService.hasCostCalculation(dto.getDate().getYear(),dto.getDate().getMonth().getValue())){
				throw new BusinessRuleException("Operação não permitida. Apuração de custo finalizada!");
			}
			return new OperationalPaymentDTO(repository.save(entity));
		}catch(BusinessRuleException e) {
			throw new BusinessRuleException(e.getMessage());
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found");
		}
	}
	@Transactional
	public OperationalPaymentDTO update(Long id, OperationalPaymentDTO dto) {
		try {
			OperationalPayment entity = repository.getOne(id);
			if(costCalculationService.hasCostCalculation(entity.getDate().getYear(),entity.getDate().getMonth().getValue())){
				throw new BusinessRuleException("Operação não permitida. Apuração de custo finalizada!");
			}
			convertToEntity(dto, entity);
			entity = repository.save(entity);
			return new OperationalPaymentDTO(entity);
		}catch(BusinessRuleException e) {
			throw new BusinessRuleException(e.getMessage());
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		}
	}
	@Transactional
	public void delete(Long id) {
		try {
			 OperationalPayment entity = repository.getOne(id);
			 if(costCalculationService.hasCostCalculation(entity.getDate().getYear(),entity.getDate().getMonth().getValue())){
				throw new BusinessRuleException("Operação não permitida. Apuração de custo finalizada!");
			 }
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
		entity.setOperationalCostType(operationalCostTypeRepository.getOne(dto.getOperationalCostType().getId()));
	}
}
