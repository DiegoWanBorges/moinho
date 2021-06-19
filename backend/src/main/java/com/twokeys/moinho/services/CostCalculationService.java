package com.twokeys.moinho.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twokeys.moinho.dto.CostCalculationDTO;
import com.twokeys.moinho.dto.CostCalculationResultDTO;
import com.twokeys.moinho.dto.ProductDTO;
import com.twokeys.moinho.dto.ProductionOrderCostLaborDTO;
import com.twokeys.moinho.dto.ProductionOrderDTO;
import com.twokeys.moinho.dto.ProductionOrderItemDTO;
import com.twokeys.moinho.dto.ProductionOrderOperationalCostDTO;
import com.twokeys.moinho.dto.ProductionOrderProducedAverageCostDTO;
import com.twokeys.moinho.dto.ProductionOrderProducedDTO;
import com.twokeys.moinho.dto.StockBalanceDTO;
import com.twokeys.moinho.entities.CostCalculation;
import com.twokeys.moinho.entities.enums.CostCalculationStatus;
import com.twokeys.moinho.entities.enums.FormulationType;
import com.twokeys.moinho.entities.enums.ProductionOrderStatus;
import com.twokeys.moinho.repositories.CostCalculationRepository;
import com.twokeys.moinho.services.exceptions.BusinessRuleException;
import com.twokeys.moinho.services.exceptions.DatabaseException;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;
import com.twokeys.moinho.services.exceptions.UntreatedException;
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
	public Page<CostCalculationDTO> findByReferenceMonthAndStatus(LocalDate startDate,LocalDate endDate,PageRequest pageRequest){
		   Page<CostCalculation> page = repository.findByReferenceMonthAndStatus(startDate,endDate,pageRequest);
		   return page.map(x -> new CostCalculationDTO(x));
	}
	
	@Transactional(readOnly=true)
	public CostCalculationResultDTO findResultById(Long id){
		try {
			 CostCalculationResultDTO result = new CostCalculationResultDTO();
			 List<ProductionOrderDTO> productionsOrders = new ArrayList<>();
			 List<StockBalanceDTO> openingStockBalance = new ArrayList<>();
			 List<StockBalanceDTO> closingStockBalance = new ArrayList<>();
			 CostCalculation entity = repository.getOne(id);
			 
			 LocalDate endDate = LocalDate.ofInstant(entity.getEndDate(), ZoneId.of("America/Sao_Paulo"));
			 
			 productionsOrders=productionOrderService.findByStartDateAndStatus(entity.getStartDate(), entity.getEndDate(), ProductionOrderStatus.APURACAO_FINALIZADA);
			 openingStockBalance = stockMovementService.stockByPreviousAndEqualDate(entity.getStockStartDate());
			 closingStockBalance = stockMovementService.stockByPreviousAndEqualDate(endDate);
			 
			 
			 result.setCostCalculation(new CostCalculationDTO(entity));
			 result.getProductionOrders().addAll(productionsOrders);	
			 result.getOpeningStockBalance().addAll(openingStockBalance);
			 result.getClosingStockBalance().addAll(closingStockBalance);
			 return result;
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		} catch (Exception e) {
			throw new UntreatedException(e.getMessage());
		}
	}
		
	@Transactional(readOnly=true)
	public Boolean hasCostCalculation(Integer year, Integer month){
		CostCalculation entity = repository.findByReferenceMonthAndYearAndMonth(year,month);
		if (entity==null) {
			return false;
		}else {
			return true;
		}
	}
	
	@Transactional
	public CostCalculationDTO calculation (CostCalculation costCalculation) {
		try {
			List<ProductionOrderDTO> listProductionOrder = new ArrayList<>();
			List<ProductionOrderOperationalCostDTO> listProductionOrderOperationalCost = new ArrayList<>();
			List<ProductionOrderCostLaborDTO> listProductionOrderCostLabor = new ArrayList<>();
			List<ProductionOrderItemDTO> listProductionOrderItem = new ArrayList<>();
			Double costUnity;
			Double averageCost;
			Double totalValue;
			Double indirectCost;
			Double totalQuantity;
			Set<ProductDTO> products = new HashSet<>();
			List<Integer> levels = new ArrayList<>();
			StockBalanceDTO stockBalance;
			ProductionOrderProducedAverageCostDTO productionOrderProducedAverageCost;
			
			/*RATEIO - DESPESAS OPERACIONAIS*/
			productionOrderOperationalCostService.prorateOperatingCost(costCalculation.getStartDate(), costCalculation.getEndDate());
			/*RATEIO - CUSTO MÃO DE OBRA*/
			productionOrderCostLaborService.laborPaymentApportionment(costCalculation.getStartDate(), costCalculation.getEndDate());
			
			logger.info("Saiu do rateio");
			
			/*FORMULAÇÃO - APURAÇÃO ITERMEDIARIO*/
			/*CALCULA O CUSTO UNITARIO PARA CADA ORDEM DE PRODUÇÃO*/
			levels = productionOrderService.distinctLevelProduced(costCalculation.getStartDate(), costCalculation.getEndDate());
			for (Integer level : levels) {
				products.clear();
				/*RECUPERA A LISTA DE PRODUÇÃO POR NIVEL DA FORMULAÇÃO*/
				listProductionOrder = productionOrderService.listByStartDateAndStatus(costCalculation.getStartDate(), costCalculation.getEndDate(),ProductionOrderStatus.ENCERRADO,FormulationType.INTERMEDIARIO,level);
				for (ProductionOrderDTO productionOrder : listProductionOrder) {
					/*CALCULA O CUSTO UNITARIO DA ORDEM DE PRODUÇÃO*/
					listProductionOrderOperationalCost = productionOrderOperationalCostService.findByIdProductionOrderId(productionOrder.getId());
					listProductionOrderCostLabor = productionOrderCostLaborService.findByIdProductionOrderId(productionOrder.getId());
					indirectCost=0.0;
					/*TOTAL DO CUSTO OPERACIONAL ABSORVIDO PELA ORDEM DE PRODUÇÃO*/
					for (ProductionOrderOperationalCostDTO productionOrderOperationalCost : listProductionOrderOperationalCost) {
						indirectCost+=productionOrderOperationalCost.getValue();
					}
					/*TOTAL DO CUSTO DE MÃO DE OBRA ABSORVIDO PELA ORDEM DE PRODUÇÃO*/
					for (ProductionOrderCostLaborDTO productionOrderCostLabor : listProductionOrderCostLabor) {
						indirectCost+=productionOrderCostLabor.getValue();
					}
					
					costUnity = (productionOrder.getTotalDirectCost()+indirectCost)/productionOrder.getTotalProduced();
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
		
			for (ProductionOrderDTO productionOrder : listProductionOrder) {
				/*CALCULA O CUSTO UNITARIO DA ORDEM DE PRODUÇÃO*/
				listProductionOrderOperationalCost = productionOrderOperationalCostService.findByIdProductionOrderId(productionOrder.getId());
				listProductionOrderCostLabor = productionOrderCostLaborService.findByIdProductionOrderId(productionOrder.getId());
				indirectCost=0.0;
				/*TOTAL DO CUSTO OPERACIONAL ABSORVIDO PELA ORDEM DE PRODUÇÃO*/
				for (ProductionOrderOperationalCostDTO productionOrderOperationalCost : listProductionOrderOperationalCost) {
					indirectCost+=productionOrderOperationalCost.getValue();
				}
				/*TOTAL DO CUSTO DE MÃO DE OBRA ABSORVIDO PELA ORDEM DE PRODUÇÃO*/
				for (ProductionOrderCostLaborDTO productionOrderCostLabor : listProductionOrderCostLabor) {
					indirectCost+=productionOrderCostLabor.getValue();
				}
				costUnity = (productionOrder.getTotalDirectCost()+indirectCost)/productionOrder.getTotalProduced();
				costUnity=Util.roundHalfUp2(costUnity);
				productionOrder.setStatus(ProductionOrderStatus.APURACAO_FINALIZADA);
				productionOrderService.updateService(productionOrder.getId(), productionOrder);
				/*ATUALIZA O CUSTO DE ESTOQUE QUE FOI EFETUADO DURANTE A ENTRADA DA PRODUÇÃO*/
				for (ProductionOrderProducedDTO productionOrderProduced : productionOrder.getProductionOrderProduceds()) {
					productionOrderProduced.setUnitCost(costUnity);
					productionOrderProducedService.updateService(productionOrderProduced);
				}
			}
				costCalculation.setStatus(CostCalculationStatus.ENCERRADO);
		return	new CostCalculationDTO(repository.save(costCalculation));
		} catch (Exception e) {
			throw new UntreatedException(e.getMessage());
		}
		
	}
	
	@Transactional
	public CostCalculationDTO insert(CostCalculationDTO dto) {
				CostCalculation entity =new CostCalculation();
				List<ProductionOrderDTO> list = productionOrderService.findByStartDateAndStatus(dto.getStartDate(), dto.getEndDate(), ProductionOrderStatus.ABERTO); 
				/*NÃO PODE TER ORDEM DE PRODUÇÃO COM STATUS ABERTO*/
				if (list.size() > 0) {
					throw new BusinessRuleException("O periodo selecionado possui ordem de produção em aberto");
				}
				list = productionOrderService.findByStartDateAndStatus(dto.getStartDate(), dto.getEndDate(), ProductionOrderStatus.ENCERRADO); 
				/*NÃO TER ORDEM DE PRODUÇÃO COM STATUS ENCERRADO*/
				if (list.size() == 0) {
					throw new BusinessRuleException("O periodo selecionado não possui ordem de produção encerrada");
				}
				dto.setStatus(CostCalculationStatus.ANDAMENTO);
				convertToEntity(dto,entity);
				entity = repository.save(entity);
				return calculation(entity);
	}
	
	@Transactional
	public void delete(Long id) {
		try {
			CostCalculation entity = repository.getOne(id);
			
			entity.setStatus(CostCalculationStatus.CANCELADO);
			repository.save(entity);
			
			List<ProductionOrderDTO> list = productionOrderService.findByStartDateAndStatus(entity.getStartDate(), entity.getEndDate(), ProductionOrderStatus.APURACAO_FINALIZADA);
			for (ProductionOrderDTO productionOrder : list) {
				/*DELETAR RATEIO MÃO DE OBRA*/
				productionOrderCostLaborService.delete(productionOrder.getId());
				/*DELETAR RATEIO CUSTO OPERACIONAL*/
				productionOrderOperationalCostService.delete(productionOrder.getId());
								
				productionOrder.setStatus(ProductionOrderStatus.ENCERRADO);
				productionOrderService.updateService(productionOrder.getId(), productionOrder);
				/*ZERAR O CUSTO DO PRODUTO PRODUZIDO*/
				for (ProductionOrderProducedDTO productionOrderProduced : productionOrder.getProductionOrderProduceds()) {
					productionOrderProduced.setUnitCost(0.0);
					productionOrderProducedService.updateService(productionOrderProduced);
				}
			}
			
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
		entity.setReferenceMonth(dto.getReferenceMonth());
	}
	
}
