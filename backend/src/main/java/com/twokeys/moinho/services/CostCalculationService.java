package com.twokeys.moinho.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twokeys.moinho.dto.CostCalculationDTO;
import com.twokeys.moinho.dto.ProductDTO;
import com.twokeys.moinho.dto.ProductionOrderDTO;
import com.twokeys.moinho.dto.ProductionOrderItemDTO;
import com.twokeys.moinho.dto.ProductionOrderProducedAverageCostDTO;
import com.twokeys.moinho.dto.ProductionOrderProducedDTO;
import com.twokeys.moinho.dto.StockBalanceDTO;
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
	@Autowired
	private ProductionOrderItemService productionOrderItemService;
	@Autowired
	private ProductService productService;
	@Autowired
	private StockMovementService stockMovementService; 
	
	@Transactional(readOnly=true)
	public CostCalculationDTO findById(Long id){
		Optional<CostCalculation> obj = repository.findById(id);
		CostCalculation entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new CostCalculationDTO(entity);
	}
	
	@Transactional
	public void calculation (Long costCalculationId) {
		CostCalculation costCalculation = repository.getOne(costCalculationId);
		List<ProductionOrderDTO> listProductionOrder = new ArrayList<>();
		List<ProductionOrderItemDTO> listProductionOrderItem = new ArrayList<>();
		Double costUnity;
		Double averageCost;
		Double totalValue;
		Double totalQuantity;
		Set<ProductDTO> products = new HashSet<>();
		List<Integer> levels = new ArrayList<>();
		StockBalanceDTO stockBalance;
		ProductionOrderProducedAverageCostDTO productionOrderProducedAverageCost;
		
		/*RATEIO - DESPESAS OPERACIONAIS*/
		productionOrderOperationalCostService.prorateOperatingCost(costCalculation.getStartDate(), costCalculation.getEndDate());
		/*RATEIO - CUSTO MÃO DE OBRA*/
		productionOrderCostLaborService.laborPaymentApportionment(costCalculation.getStartDate(), costCalculation.getEndDate());
		
		
		/*FORMULAÇÃO - APURAÇÃO ITERMEDIARIO*/
		/*CALCULA O CUSTO UNITARIO PARA CADA ORDEM DE PRODUÇÃO*/
		levels = productionOrderService.distinctLevelProduced(costCalculation.getStartDate(), costCalculation.getEndDate());
		for (Integer level : levels) {
			products.clear();
			/*RECUPERA A LISTA DE PRODUÇÃO POR NIVEL DA FORMULAÇÃO*/
			listProductionOrder = productionOrderService.listByStartDateAndStatus(costCalculation.getStartDate(), costCalculation.getEndDate(),ProductionOrderStatus.ENCERRADO,FormulationType.INTERMEDIARIO,level);
			for (ProductionOrderDTO productionOrder : listProductionOrder) {
				/*CALCULA O CUSTO UNITARIO DA ORDEM DE PRODUÇÃO*/
				costUnity = (productionOrder.getTotalDirectCost()+productionOrder.getTotalIndirectCost())/productionOrder.getTotalProduced();
				costUnity=Util.roundHalfUp2(costUnity);
				productionOrder.setStatus(ProductionOrderStatus.APURACAO_FINALIZADA);
				productionOrderService.updateService(productionOrder.getId(), productionOrder);
				/*ATUALIZA O CUSTO DE ESTOQUE QUE FOI EFETUADO DURANTE A ENTRADA DA PRODUÇÃO*/
				for (ProductionOrderProducedDTO productionOrderProduced : productionOrder.getProductionOrderProduceds()) {
					products.add(productionOrderProduced.getProduct());
					productionOrderProduced.setUnitCost(costUnity);
					productionOrderProducedService.updateService(productionOrderProduced);
				}
			}	
			/* ATUALIZA O CUSTO DAS PRODUÇÕES QUE UTILIZARAM O PRODUTO 
			 * INTERMEDIARIO COMO MATERIA PRIMA*/
			for (ProductDTO product : products) {
				/*RECUPERA O PRODUTO COM CUSTO MÉDIO ATUALIZADO*/
				product = productService.findById(product.getId());
				
				listProductionOrderItem = productionOrderItemService.findByDateAndProduct(costCalculation.getStartDate(), costCalculation.getEndDate(), product.getId());
				for (ProductionOrderItemDTO productionOrderItem : listProductionOrderItem) {
					logger.info("Produto atualizando: " + product.getName());
					/*Estoque Inicial*/
					stockBalance = stockMovementService.stockByProductAndPreviousAndEqualDate(product.getId(), costCalculation.getStockStartDate());
					/*Custo Médio da produção*/
					productionOrderProducedAverageCost = productionOrderProducedService.producedAverageCost(product.getId(), costCalculation.getStartDate(), costCalculation.getEndDate());
					totalValue = (stockBalance.getBalance()*stockBalance.getAverageCost())+(productionOrderProducedAverageCost.getAverageCost() * productionOrderProducedAverageCost.getTotalProduced()); 
					totalQuantity = (stockBalance.getBalance()+productionOrderProducedAverageCost.getTotalProduced());
					averageCost=Util.roundHalfUp2(totalValue/totalQuantity);
					/*Atualiza o custo*/
					productionOrderItem.setCost(averageCost);
					productionOrderItemService.updateService(productionOrderItem);
				}
			}
		}
		
		/*FORMULAÇÃO - APURAÇÃO PRODUTO ACABADO*/
		
		listProductionOrder.clear();
		listProductionOrder = productionOrderService.listByStartDateAndStatus(costCalculation.getStartDate(), costCalculation.getEndDate(),ProductionOrderStatus.ENCERRADO,FormulationType.ACABADO,null);
		logger.info("CHEGOU");
	
		for (ProductionOrderDTO productionOrder : listProductionOrder) {
			/*CALCULA O CUSTO UNITARIO DA ORDEM DE PRODUÇÃO*/
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
