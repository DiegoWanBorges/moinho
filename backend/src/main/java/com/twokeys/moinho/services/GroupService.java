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

import com.twokeys.moinho.dto.GroupDTO;
import com.twokeys.moinho.entities.Group;
import com.twokeys.moinho.repositories.GroupRepository;
import com.twokeys.moinho.services.exceptions.DatabaseException;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;



@Service
public class GroupService {
	@Autowired
	private GroupRepository repository;
	
	@Transactional(readOnly=true)
	public List<GroupDTO> findByNameLikeIgnoreCase(String name){
		String nameConcat = "%"+name+"%";
		
		List<Group> list =  repository.findByNameLikeIgnoreCase(nameConcat);
		return list.stream().map(x -> new GroupDTO(x)).collect(Collectors.toList());	
	}
	@Transactional(readOnly=true)
	public Page<GroupDTO> findAllPaged(String name,PageRequest pageRequest){
	String nameConcat ="%"+name+"%";
	Page<Group> list = repository.findByNameLikeIgnoreCase(nameConcat,pageRequest);
		return list.map(x -> new GroupDTO(x));
		
	}
	@Transactional(readOnly=true)
	public GroupDTO findById(Long id){
		Optional<Group> obj = repository.findById(id);
		Group entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new GroupDTO(entity);
	}
	@Transactional
	public GroupDTO insert(GroupDTO dto) {
			Group entity =new Group();
			entity.setName(dto.getName());
			return new GroupDTO(repository.save(entity));
	}
	@Transactional
	public GroupDTO update(Long id, GroupDTO dto) {
		try {
			Group entity = repository.getOne(id);
			entity.setName(dto.getName());
			entity = repository.save(entity);
			return new GroupDTO(entity);
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
