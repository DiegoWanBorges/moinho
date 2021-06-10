package com.twokeys.moinho.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twokeys.moinho.dto.CostCalculationDTO;
import com.twokeys.moinho.dto.ProductionOrderDTO;
import com.twokeys.moinho.dto.ProductionOrderProducedDTO;
import com.twokeys.moinho.entities.CostCalculation;
import com.twokeys.moinho.entities.enums.FormulationType;
import com.twokeys.moinho.entities.enums.ProductionOrderStatus;
import com.twokeys.moinho.repositories.CostCalculationRepository;
import com.twokeys.moinho.services.exceptions.DatabaseException;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;
import com.twokeys.moinho.util.Util;

@Service
public class CostCalculationService {
	protected final Log logger = LogFactory.getLog(getClass());
	@Autowired
	private CostCalculationRepository repository;
	@Autowired
	private ProductionOrderCostLaborService productionOrderCostLaborService;
	@Autowired
	private ProductionOrderOperationalCostService productionOrderOperationalCostService; 
	@Autowired
	private ProductionOrderService productionOrderService;
	@Autowired
	private ProductionOrderProducedService productionOrderProducedService;
	
	@Transactional(readOnly=true)
	public CostCalculationDTO findById(Long id){
		Optional<CostCalculation> obj = repository.findById(id);
		CostCalculation entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new CostCalculationDTO(entity);
	}
	
	@Transactional
	public void calculation (Long costCalculationId) {
		CostCalculation costCalculation = repository.getOne(costCalculationId);
		Double costUnity;
		
		/*RATEIO - DESPESAS OPERACIONAIS*/
		productionOrderOperationalCostService.prorateOperatingCost(costCalculation.getStartDate(), costCalculation.getEndDate());
		/*RATEIO - CUSTO MÃO DE OBRA*/
		productionOrderCostLaborService.laborPaymentApportionment(costCalculation.getStartDate(), costCalculation.getEndDate());
		
		/*FORMULAÇÃO - ITERMEDIARIO*/
		/*CALCULA O CUSTO UNITARIO PARA CADA ORDEM DE PRODUÇÃO*/
		List<ProductionOrderDTO> list = productionOrderService.listByStartDateAndStatus(costCalculation.getStartDate(), costCalculation.getEndDate(),ProductionOrderStatus.ENCERRADO,FormulationType.INTERMEDIARIO);
		for (ProductionOrderDTO productionOrder : list) {
			/*CALCULA O CUSTO UNITARIO*/
			costUnity = (productionOrder.getTotalDirectCost()+productionOrder.getTotalIndirectCost())/productionOrder.getTotalProduced();
			costUnity=Util.roundHalfUp2(costUnity);
			productionOrder.setStatus(ProductionOrderStatus.APURACAO_FINALIZADA);
			productionOrderService.updateService(productionOrder.getId(), productionOrder);
			/*ATUALIZA O CUSTO DE ESTOQUE QUE FOI EFETUADO DURANTE A ENTRADA DA PRODUÇÃO*/
			for (ProductionOrderProducedDTO productionOrderProduced : productionOrder.getProductionOrderProduceds()) {
				productionOrderProduced.setUnitCost(costUnity);
				productionOrderProducedService.updateService(productionOrderProduced);
			}
		}
		/*ATUALIZA PRODUÇÕES QUE UTILIZARAM O PRODUTO INTERMEDIARIO*/
		
		
		
		
		/*FORMULAÇÃO - ACABADO*/
		/*CALCULA O CUSTO UNITARIO PARA CADA ORDEM DE PRODUÇÃO*/
		list.clear();
		list = productionOrderService.listByStartDateAndStatus(costCalculation.getStartDate(), costCalculation.getEndDate(),ProductionOrderStatus.ENCERRADO,FormulationType.INTERMEDIARIO);
		
	}
	
	@Transactional
	public CostCalculationDTO insert(CostCalculationDTO dto) {
			CostCalculation entity =new CostCalculation();
			convertToEntity(dto,entity);
			return new CostCalculationDTO(repository.save(entity));
	}
	@Transactional
	public CostCalculationDTO update(Long id, CostCalculationDTO dto) {
		try {
			CostCalculation entity = repository.getOne(id);
			convertToEntity(dto,entity);
			entity = repository.save(entity);
			return new CostCalculationDTO(entity);
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
	public void convertToEntity(CostCalculationDTO dto,CostCalculation entity) {
		entity.setStartDate(dto.getStartDate());
		entity.setEndDate(dto.getEndDate());
		entity.setStockStartDate(dto.getStockStartDate());
		entity.setStatus(dto.getStatus());
	}
}
