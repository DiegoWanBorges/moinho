package com.twokeys.moinho.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twokeys.moinho.dto.FormulationItemDTO;
import com.twokeys.moinho.entities.Formulation;
import com.twokeys.moinho.entities.FormulationItem;
import com.twokeys.moinho.entities.Product;
import com.twokeys.moinho.entities.pk.FormulationItemPK;
import com.twokeys.moinho.repositories.FormulationItemRepository;
import com.twokeys.moinho.repositories.FormulationRepository;
import com.twokeys.moinho.repositories.ProductRepository;
import com.twokeys.moinho.services.exceptions.DatabaseException;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;



@Service
public class FormulationItemService {
	protected final Log logger = LogFactory.getLog(getClass());
	@Autowired
	private FormulationItemRepository repository;
	@Autowired 
	private ProductRepository productRepository; 
	@Autowired
	private FormulationRepository formulationRepository;
	
	@Transactional(readOnly=true)
	public FormulationItemDTO findById(Long idFormulation, Long idProduct){
		FormulationItemPK id =new FormulationItemPK();
		Formulation formulation = new Formulation();
		
		formulation.setId(idFormulation);
		id.setFormulation(formulation);
		id.setProduct(productRepository.getOne(idProduct));
		
		
		Optional<FormulationItem> obj = repository.findById(id);
		FormulationItem entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new FormulationItemDTO(entity);
	}
	@Transactional
	public FormulationItemDTO insert(FormulationItemDTO dto) {
		try {
			FormulationItem entity =new FormulationItem();
			convertToEntity(dto, entity);
			return new FormulationItemDTO(repository.save(entity));
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found");
		}
	}
	@Transactional
	public FormulationItemDTO update(FormulationItemDTO dto) {
		try {
			FormulationItemPK id = new FormulationItemPK();
			Formulation formulation = new Formulation();
			Product product = new Product();
			formulation.setId(dto.getFormulationId());
			product.setId(dto.getProduct().getId());
			id.setFormulation(formulation);
			id.setProduct(product);
			
			FormulationItem entity = repository.getOne(id);
			
			convertToEntity(dto, entity);
			entity = repository.save(entity);
			return new FormulationItemDTO(entity);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: ");
		}
	}
	public void delete(Long idFormulation, Long idProduct) {
		try {
			FormulationItemPK id = new FormulationItemPK();
			Formulation formulation = new Formulation();
			Product product = new Product();
			formulation.setId(idFormulation);
			product.setId(idProduct);
			id.setFormulation(formulation);
			id.setProduct(product);
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Formulation id: " + idFormulation + " / Product id:" + idProduct + " Not found");
		}catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
		
	}
	public void convertToEntity(FormulationItemDTO dto, FormulationItem entity) {
		Formulation formulation = formulationRepository.getOne(dto.getFormulationId());
		Product product = productRepository.getOne(dto.getProduct().getId());
		
		entity.setProduct(product);
		entity.setFormulation(formulation);
		entity.setQuantity(dto.getQuantity());
		entity.setType(dto.getType());
		entity.setRound(dto.getRound());
		entity.setRawMaterial(dto.getRawMaterial());
		
	}
}
