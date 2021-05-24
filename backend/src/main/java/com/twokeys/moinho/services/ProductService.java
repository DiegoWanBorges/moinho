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

import com.twokeys.moinho.dto.ProductDTO;
import com.twokeys.moinho.entities.Product;
import com.twokeys.moinho.repositories.GroupRepository;
import com.twokeys.moinho.repositories.ProductRepository;
import com.twokeys.moinho.repositories.UnityRepository;
import com.twokeys.moinho.services.exceptions.DatabaseException;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;
import com.twokeys.moinho.services.exceptions.UntreatedException;



@Service
public class ProductService {
	@Autowired
	private ProductRepository repository;
	@Autowired
	private UnityRepository unityRepository;
	@Autowired
	private GroupRepository groupRepository;
	
	@Transactional(readOnly=true)
	public List<ProductDTO> findByNameLikeIgnoreCase(String name){
		String nameConcat = "%"+name+"%";
		List<Product> list =  repository.findByNameLikeIgnoreCase(nameConcat);
		return list.stream().map(x -> new ProductDTO(x)).collect(Collectors.toList());
	}
	@Transactional(readOnly=true)
	public List<ProductDTO> findProductNotInFormulation(Long id){
		List<Product> list =  repository.findProductNotInFormulation(id);
		return list.stream().map(x -> new ProductDTO(x)).collect(Collectors.toList());
	}
	@Transactional(readOnly=true)
	public List<ProductDTO> findProductProducedByFormulation(Long id){
		List<Product> list =  repository.findProductProducedByFormulation(id);
		return list.stream().map(x -> new ProductDTO(x)).collect(Collectors.toList());
	}
	@Transactional(readOnly=true)
	public Page<ProductDTO> findAllPaged(String name,PageRequest pageRequest){
	String nameConcat ="%"+name+"%";
	Page<Product> list = repository.findByNameLikeIgnoreCase(nameConcat,pageRequest);
		return list.map(x -> new ProductDTO(x));
	}
	@Transactional(readOnly=true)
	public ProductDTO findById(Long id){
		Optional<Product> obj = repository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ProductDTO(entity);
	}
	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		try {
			 Product entity =new Product();
			 convertToEntity(dto, entity);
			 return new ProductDTO(repository.save(entity));
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found");
		}catch (DataIntegrityViolationException e ) {
			throw new DatabaseException("Database integrity reference");
		}catch(Exception e) {
			throw new UntreatedException(e.getMessage());
		}
	}
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product entity = repository.getOne(id);
			convertToEntity(dto, entity);
			entity = repository.save(entity);
			return new ProductDTO(entity);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found");
		}catch (DataIntegrityViolationException e ) {
			throw new DatabaseException("Database integrity reference");
		}catch(Exception e) {
			throw new ResourceNotFoundException("Gereric error found");
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
	public void convertToEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPackaging(dto.getPackaging());
		entity.setNetWeight(dto.getNetWeight());
		entity.setGrossWeight(dto.getGrossWeight());
		entity.setValidityDays(dto.getValidityDays());
		entity.setUnity(unityRepository.getOne(dto.getUnity().getId()));
		entity.setGroup(groupRepository.getOne(dto.getGroup().getId()));
	}
}
