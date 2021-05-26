package com.twokeys.moinho.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twokeys.moinho.dto.PalletStatusDTO;
import com.twokeys.moinho.entities.PalletStatus;
import com.twokeys.moinho.repositories.PalletStatusRepository;
import com.twokeys.moinho.services.exceptions.DatabaseException;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;



@Service
public class PalletStatusService {
	@Autowired
	private PalletStatusRepository repository;
	
	@Transactional(readOnly=true)
	public Page<PalletStatusDTO> findAllPaged(String name,PageRequest pageRequest){
	String nameConcat ="%"+name+"%";
	Page<PalletStatus> list = repository.findByNameLikeIgnoreCase(nameConcat,pageRequest);
		return list.map(x -> new PalletStatusDTO(x));
	}
	
	@Transactional(readOnly=true)
	public List<PalletStatusDTO> findByNameLikeIgnoreCase(String name){
		String nameConcat = "%"+name+"%";
		
		List<PalletStatus> list =  repository.findByNameLikeIgnoreCase(nameConcat);
		return list.stream().map(x -> new PalletStatusDTO(x)).collect(Collectors.toList());
		
	}
	@Transactional(readOnly=true)
	public PalletStatusDTO findById(Long id){
		Optional<PalletStatus> obj = repository.findById(id);
		PalletStatus entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new PalletStatusDTO(entity);
	}
	@Transactional
	public PalletStatusDTO insert(PalletStatusDTO dto) {
			PalletStatus entity =new PalletStatus();
			entity.setName(dto.getName());
			return new PalletStatusDTO(repository.save(entity));
	}
	@Transactional
	public PalletStatusDTO update(Long id, PalletStatusDTO dto) {
		try {
			PalletStatus entity = repository.getOne(id);
			entity.setName(dto.getName());
			entity = repository.save(entity);
			return new PalletStatusDTO(entity);
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
