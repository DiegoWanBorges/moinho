package com.twokeys.moinho.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twokeys.moinho.dto.FormulationDTO;
import com.twokeys.moinho.dto.FormulationItemsDTO;
import com.twokeys.moinho.dto.ProductionOrderDTO;
import com.twokeys.moinho.dto.ProductionOrderItemsDTO;
import com.twokeys.moinho.entities.ProductionOrder;
import com.twokeys.moinho.entities.enums.ProductionOrderItemsType;
import com.twokeys.moinho.entities.enums.ProductionOrderStatus;
import com.twokeys.moinho.repositories.FormulationRepository;
import com.twokeys.moinho.repositories.ProductionOrderRepository;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;



@Service
public class ProductionOrderService {
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private ProductionOrderRepository repository;
	
	@Autowired
	private FormulationRepository formulationRepository;
	
	@Autowired
	private FormulationService formulationService;
	
	@Autowired
	private ProductionOrderItemsService productionOrderItemsService;
	
	
	@Transactional
	public ProductionOrderDTO createProductionOrder(Long formulationId, Double ammount,Boolean persistence){
		FormulationDTO formulation = formulationService.findById(formulationId);
		ProductionOrderDTO productionOrder = new ProductionOrderDTO();
		ProductionOrderItemsDTO productionOrderItems; 
		Double quantityItem =0.0;
	
		for (FormulationItemsDTO formulationItems : formulation.getFormulationItems()) {
			quantityItem= (ammount*formulationItems.getQuantity())/formulation.getCoefficient();
			productionOrderItems = new ProductionOrderItemsDTO();
			productionOrderItems.setCost(0.0);
			if (formulationItems.getRound()==1) {
				productionOrderItems.setQuantity( Double.valueOf(new BigDecimal(quantityItem).setScale(0,RoundingMode.UP).toString()));
			}else {
				productionOrderItems.setQuantity(quantityItem);
			}
			productionOrderItems.setType(ProductionOrderItemsType.NORMAL);
			productionOrderItems.setProduct(formulationItems.getProduct());
			productionOrder.getProductionOrderItems().add(productionOrderItems);
		}
		productionOrder.setEmission(Instant.now());
		productionOrder.setExpectedAmount(ammount);
		productionOrder.setStatus(ProductionOrderStatus.ABERTO);
		productionOrder.setFormulation(formulation);
		if (persistence) {
			return insert(productionOrder);
		}else {
			return productionOrder;
		}
		
	}
	
	@Transactional(readOnly=true)
	public ProductionOrderDTO findById(Long id){
		Optional<ProductionOrder> obj = repository.findById(id);
		ProductionOrder entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ProductionOrderDTO(entity);
	}
	
	@Transactional
	public ProductionOrderDTO insert(ProductionOrderDTO dto) {
		try {
			ProductionOrder entity =new ProductionOrder();
			convertToEntity(dto, entity);
			entity=repository.save(entity);
			List<ProductionOrderItemsDTO> list = new ArrayList<>();
			
			for(ProductionOrderItemsDTO item : dto.getProductionOrderItems()) {
				item.setProductionOrderId(entity.getId());
			}
			
			list=productionOrderItemsService.insert(dto.getProductionOrderItems());
			
			return new ProductionOrderDTO(entity,list);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found");
		}
	}
	
	@Transactional
	public ProductionOrderDTO update(Long id, ProductionOrderDTO dto) {
		try {
			ProductionOrder entity = repository.getOne(id);
			convertToEntity(dto, entity);
			entity = repository.save(entity);
			return new ProductionOrderDTO(entity);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		}
	}
	
	public void convertToEntity(ProductionOrderDTO dto, ProductionOrder entity) {
		entity.setEmission(dto.getEmission());
		entity.setEndDate(dto.getEndDate());
		entity.setStartDate(dto.getStartDate());
		entity.setExpectedAmount(dto.getExpectedAmount());
		entity.setObservation(dto.getObservation());
		entity.setStatus(dto.getStatus());
		entity.setFormulation(formulationRepository.getOne(dto.getFormulation().getId()));
	}
}
