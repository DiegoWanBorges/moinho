package com.twokeys.moinho.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
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
import com.twokeys.moinho.entities.enums.StockMovementType;
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
			 List<StockBalanceDTO> purchaseStockBalance = new ArrayList<>();
			 List<StockBalanceDTO> adjustmentStockBalance = new ArrayList<>();
			 List<ProductionOrderProducedAverageCostDTO> productionOrderProducedAverageCosts = new ArrayList<>();
			 CostCalculation entity = repository.getOne(id);
			 
			 LocalDate startDate = LocalDate.ofInstant(entity.getStartDate(), ZoneId.of("America/Sao_Paulo"));
			 LocalDate endDate = LocalDate.ofInstant(entity.getEndDate(), ZoneId.of("America/Sao_Paulo"));
			 
			 productionsOrders=productionOrderService.findByStartDateAndStatus(entity.getStartDate(), entity.getEndDate(), ProductionOrderStatus.APURACAO_FINALIZADA);
			 openingStockBalance = stockMovementService.stockByPreviousAndEqualDate(entity.getStockStartDate());
			 closingStockBalance = stockMovementService.stockByPreviousAndEqualDate(endDate);
			 productionOrderProducedAverageCosts = productionOrderProducedService.findProducedStartDate(entity.getStartDate(), entity.getEndDate());
			 purchaseStockBalance = stockMovementService.stockByDateBetweenAndType(startDate,endDate,StockMovementType.COMPRA);
			 adjustmentStockBalance = stockMovementService.stockByDateBetweenAndType(startDate,endDate,StockMovementType.AJUSTE_ESTOQUE);
			 result.setCostCalculation(new CostCalculationDTO(entity));
			 result.getProductionOrders().addAll(productionsOrders);	
			 result.getOpeningStockBalance().addAll(openingStockBalance);
			 result.getClosingStockBalance().addAll(closingStockBalance);
			 result.getProductionOrderProducedAverageCosts().addAll(productionOrderProducedAverageCosts);
			 result.getPurchaseStockBalance().addAll(purchaseStockBalance);
			 result.getAdjustmentStockBalance().addAll(adjustmentStockBalance);
			 return result;
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		} catch (Exception e) {
			throw new UntreatedException(e.getMessage());
		}
	}
		
	@Transactional(readOnly=true)
	public void hasCostCalculation(LocalDate launchDate){
		try {
			LocalDate maxDate = repository.findMaxReferenceMonth();
			if (maxDate != null) {
				maxDate = maxDate.with(TemporalAdjusters.lastDayOfMonth());
				if (launchDate.isBefore(maxDate) || launchDate.isEqual(maxDate)) {
					throw new BusinessRuleException("Opera????o n??o permitida. Data possui apura????o finalizada!");
				}	
			}
		} catch (BusinessRuleException e) {
			throw new BusinessRuleException(e.getMessage());
		} catch (Exception e) {
			throw new UntreatedException(e.getMessage());
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
			productionOrderOperationalCostService.prorateOperationalCost(costCalculation.getStartDate(), costCalculation.getEndDate());
			/*RATEIO - CUSTO M??O DE OBRA*/
			productionOrderCostLaborService.prorateCostLabor(costCalculation.getStartDate(), costCalculation.getEndDate());
			
			
			/*ATUALIZA O CUSTO M??DIO*/
			updateAverageCostProductionConsumptions(costCalculation.getStartDate(), costCalculation.getEndDate());
			
			/*FORMULA????O - APURA????O ITERMEDIARIO*/
			/*CALCULA O CUSTO UNITARIO PARA CADA ORDEM DE PRODU????O*/
			levels = productionOrderService.distinctLevelProduced(costCalculation.getStartDate(), costCalculation.getEndDate());
			for (Integer level : levels) {
				products.clear();
				/*RECUPERA A LISTA DE PRODU????O POR NIVEL DA FORMULA????O*/
				listProductionOrder = productionOrderService.listByStartDateAndStatus(costCalculation.getStartDate(), costCalculation.getEndDate(),ProductionOrderStatus.ENCERRADO,FormulationType.INTERMEDIARIO,level);
				for (ProductionOrderDTO productionOrder : listProductionOrder) {
					/*CALCULA O CUSTO UNITARIO DA ORDEM DE PRODU????O*/
					listProductionOrderOperationalCost = productionOrderOperationalCostService.findByIdProductionOrderId(productionOrder.getId());
					listProductionOrderCostLabor = productionOrderCostLaborService.findByIdProductionOrderId(productionOrder.getId());
					indirectCost=0.0;
					/*TOTAL DO CUSTO OPERACIONAL ABSORVIDO PELA ORDEM DE PRODU????O*/
					for (ProductionOrderOperationalCostDTO productionOrderOperationalCost : listProductionOrderOperationalCost) {
						indirectCost+=productionOrderOperationalCost.getValue();
					}
					/*TOTAL DO CUSTO DE M??O DE OBRA ABSORVIDO PELA ORDEM DE PRODU????O*/
					for (ProductionOrderCostLaborDTO productionOrderCostLabor : listProductionOrderCostLabor) {
						indirectCost+=productionOrderCostLabor.getValue();
					}
					
					costUnity = (productionOrder.getTotalDirectCost()+indirectCost)/productionOrder.getTotalProduced();
					costUnity=Util.roundHalfUp2(costUnity);
					productionOrder.setStatus(ProductionOrderStatus.APURACAO_FINALIZADA);
					productionOrderService.updateService(productionOrder.getId(), productionOrder);
					/*ATUALIZA O CUSTO DE ESTOQUE QUE FOI EFETUADO DURANTE A ENTRADA DA PRODU????O*/
					for (ProductionOrderProducedDTO productionOrderProduced : productionOrder.getProductionOrderProduceds()) {
						products.add(productionOrderProduced.getProduct());
						productionOrderProduced.setUnitCost(costUnity);
						productionOrderProducedService.updateService(productionOrderProduced);
					}
				}	
				/* ATUALIZA O CUSTO DAS PRODU????ES QUE UTILIZARAM O PRODUTO 
				 * INTERMEDIARIO COMO MATERIA PRIMA*/
				for (ProductDTO product : products) {
					/*RECUPERA O PRODUTO COM CUSTO M??DIO ATUALIZADO*/
					product = productService.findById(product.getId());
					
					listProductionOrderItem = productionOrderItemService.findByDateAndProduct(costCalculation.getStartDate(), costCalculation.getEndDate(), product.getId());
					for (ProductionOrderItemDTO productionOrderItem : listProductionOrderItem) {
						logger.info("Produto atualizando: " + product.getName());
						/*Estoque Inicial*/
						stockBalance = stockMovementService.stockByProductAndPreviousAndEqualDate(product.getId(), costCalculation.getStockStartDate());
						/*Custo M??dio da produ????o*/
						productionOrderProducedAverageCost = productionOrderProducedService.findProducedByProductAndStartDate(product.getId(), costCalculation.getStartDate(), costCalculation.getEndDate());
						totalValue = (stockBalance.getBalance()*stockBalance.getAverageCost())+(productionOrderProducedAverageCost.getAverageCost() * productionOrderProducedAverageCost.getTotalProduced()); 
						totalQuantity = (stockBalance.getBalance()+productionOrderProducedAverageCost.getTotalProduced());
						averageCost=Util.roundHalfUp2(totalValue/totalQuantity);
						/*Atualiza o custo*/
						productionOrderItem.setCost(averageCost);
						productionOrderItemService.updateService(productionOrderItem);
					}
				}
			}
			
			/*FORMULA????O - APURA????O PRODUTO ACABADO*/
			listProductionOrder.clear();
			listProductionOrder = productionOrderService.listByStartDateAndStatus(costCalculation.getStartDate(), costCalculation.getEndDate(),ProductionOrderStatus.ENCERRADO,FormulationType.ACABADO,null);
		
			for (ProductionOrderDTO productionOrder : listProductionOrder) {
				/*CALCULA O CUSTO UNITARIO DA ORDEM DE PRODU????O*/
				listProductionOrderOperationalCost = productionOrderOperationalCostService.findByIdProductionOrderId(productionOrder.getId());
				listProductionOrderCostLabor = productionOrderCostLaborService.findByIdProductionOrderId(productionOrder.getId());
				indirectCost=0.0;
				/*TOTAL DO CUSTO OPERACIONAL ABSORVIDO PELA ORDEM DE PRODU????O*/
				for (ProductionOrderOperationalCostDTO productionOrderOperationalCost : listProductionOrderOperationalCost) {
					indirectCost+=productionOrderOperationalCost.getValue();
				}
				/*TOTAL DO CUSTO DE M??O DE OBRA ABSORVIDO PELA ORDEM DE PRODU????O*/
				for (ProductionOrderCostLaborDTO productionOrderCostLabor : listProductionOrderCostLabor) {
					indirectCost+=productionOrderCostLabor.getValue();
				}
				costUnity = (productionOrder.getTotalDirectCost()+indirectCost)/productionOrder.getTotalProduced();
				costUnity=Util.roundHalfUp2(costUnity);
				productionOrder.setStatus(ProductionOrderStatus.APURACAO_FINALIZADA);
				productionOrderService.updateService(productionOrder.getId(), productionOrder);
				/*ATUALIZA O CUSTO DE ESTOQUE QUE FOI EFETUADO DURANTE A ENTRADA DA PRODU????O*/
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
				/*N??O PODE TER ORDEM DE PRODU????O COM STATUS ABERTO*/
				if (list.size() > 0) {
					throw new BusinessRuleException("O periodo selecionado possui ordem de produ????o em aberto");
				}
				list = productionOrderService.findByStartDateAndStatus(dto.getStartDate(), dto.getEndDate(), ProductionOrderStatus.ENCERRADO); 
				/*N??O TER ORDEM DE PRODU????O COM STATUS ENCERRADO*/
				if (list.size() == 0) {
					throw new BusinessRuleException("O periodo selecionado n??o possui ordem de produ????o encerrada");
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
				/*DELETAR RATEIO M??O DE OBRA*/
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
	
	public void updateAverageCostProductionConsumptions(Instant startDate,Instant endDate) {
		try {
			List<ProductionOrderItemDTO> list = new ArrayList<>();
			StockBalanceDTO stock;
			
			list = productionOrderItemService.findByDate(startDate, endDate);
			for (ProductionOrderItemDTO productionOrderItem : list) {
				stock = stockMovementService.stockByProductAndPreviousAndEqualDate(productionOrderItem.getProduct().getId(), Util.toLocalDate(endDate));
				productionOrderItem.setCost(stock.getAverageCost());
				productionOrderItemService.updateService(productionOrderItem);
			}
		} catch (Exception e) {
			throw new UntreatedException(e.getMessage());
		}
	}
	
	public void convertToEntity(CostCalculationDTO dto,CostCalculation entity) {
		entity.setStartDate(dto.getStartDate());
		entity.setEndDate(dto.getEndDate());
		entity.setStockStartDate(dto.getStockStartDate());
		entity.setStatus(dto.getStatus());
		entity.setReferenceMonth(LocalDate.of(dto.getReferenceMonth().getYear(), dto.getReferenceMonth().getMonth().getValue(), 1));
	}
	
	
}
