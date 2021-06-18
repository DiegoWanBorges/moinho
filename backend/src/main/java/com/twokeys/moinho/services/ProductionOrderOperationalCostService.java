package com.twokeys.moinho.services;

import java.math.BigInteger;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twokeys.moinho.dto.ProductionOrderOperationalCostDTO;
import com.twokeys.moinho.entities.OperationalCostType;
import com.twokeys.moinho.entities.ProductionOrder;
import com.twokeys.moinho.entities.ProductionOrderOperationalCost;
import com.twokeys.moinho.entities.enums.ProductionApportionmentType;
import com.twokeys.moinho.repositories.OperationalPaymentRepository;
import com.twokeys.moinho.repositories.ProductionOrderItemRepository;
import com.twokeys.moinho.repositories.ProductionOrderOperationalCostRepository;
import com.twokeys.moinho.repositories.ProductionOrderRepository;
import com.twokeys.moinho.services.exceptions.DatabaseException;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;
import com.twokeys.moinho.services.exceptions.UntreatedException;
import com.twokeys.moinho.util.Util;



@Service
public class ProductionOrderOperationalCostService {
	protected final Log logger = LogFactory.getLog(getClass());
	@Autowired
	private ProductionOrderOperationalCostRepository repository;
	
	@Autowired
	private ProductionOrderRepository productionOrderRepository;
	@Autowired
	private OperationalPaymentRepository operationalPaymentRepository; 
	@Autowired
	private ProductionOrderItemRepository productionOrderItemsRepository;
	
	@Transactional(readOnly = true)
	public List<ProductionOrderOperationalCostDTO> findByIdProductionOrderId(Long id){
		List<ProductionOrderOperationalCost> list = repository.findByIdProductionOrderId(id);
		return list.stream().map(x -> new ProductionOrderOperationalCostDTO(x)).collect(Collectors.toList());
	}
	
	
	@Transactional
	public void prorateOperatingCost(Instant startDate, Instant endDate){
		try {
			List<Object[]> listOperationalPayment = operationalPaymentRepository.listOperationalCostGroupByTypeApportionment(startDate, endDate);
			
			List<ProductionOrder> listProductionOrder = new ArrayList<>();
			
			ProductionApportionmentType type;
			Long productionDurationTotal;
			Double totalRawMaterial;
			Double percent;
			Double proratedAmount;

			ProductionOrderOperationalCost productionOrderOperationalCost;
			BigInteger apportionmentId;
			Long id;
			
			for(Object[] inf : listOperationalPayment) {
				type = ProductionApportionmentType.valueOf((Integer)inf[2]);
				
				switch (type) {
					case TEMPO_PRODUCAO:
						productionDurationTotal =0L;
						listProductionOrder.clear();
						apportionmentId=(BigInteger) inf[0];
						id = apportionmentId.longValue();
						/*Recupera as Ordens de produções, vinculadas aos rateios operacionais*/
						listProductionOrder = productionOrderRepository.listProductionOrderByStartDateAndFormulationSector(startDate, endDate,id);
					
						for(ProductionOrder item: listProductionOrder) {
							productionDurationTotal+=item.getProductionMinutes();
						}
						
						for(ProductionOrder item: listProductionOrder) {
							proratedAmount =0.0;
							percent = Util.roundHalfUp2(Double.valueOf(item.getProductionMinutes()) / productionDurationTotal);
							proratedAmount=Util.roundHalfUp2(percent * (Double) inf[3]);
							productionOrderOperationalCost = new ProductionOrderOperationalCost();
							productionOrderOperationalCost.setValue(proratedAmount);
							productionOrderOperationalCost.setProductionOrder(item);
							productionOrderOperationalCost.setOperationalCostType(new OperationalCostType(id,"",type));
							repository.saveAndFlush(productionOrderOperationalCost);
						}
						break;
					case VOLUME_MATERIA_PRIMA:
						totalRawMaterial =0.0;
						listProductionOrder.clear();
						apportionmentId=(BigInteger) inf[0];
						id = apportionmentId.longValue();
						/*Recupera as Ordens de produções, vinculadas aos rateios operacionais*/
						listProductionOrder = productionOrderRepository.listProductionOrderByStartDateAndFormulationApportionment(startDate, endDate,id);
						
						for(ProductionOrder item: listProductionOrder) {
							totalRawMaterial+=productionOrderItemsRepository.findTotalRawMaterial(item);
						}
						
						for(ProductionOrder item: listProductionOrder) {
							logger.info("Op number: "+ item.getId());
							logger.info("Apportionment Type id"+ id);
							logger.info("Type: "+ type);
							
							percent= Util.roundHalfUp2(productionOrderItemsRepository.findTotalRawMaterial(item)/totalRawMaterial);
							proratedAmount=Util.roundHalfUp2(percent * (Double) inf[3]);
							
							productionOrderOperationalCost = new ProductionOrderOperationalCost();
							productionOrderOperationalCost.setValue(proratedAmount);
							productionOrderOperationalCost.setProductionOrder(item);
							productionOrderOperationalCost.setOperationalCostType(new OperationalCostType(id,"",type));
							repository.saveAndFlush(productionOrderOperationalCost);
						}
						break;
					default:
						break;
				}	
			}
		}catch(Exception e) {
			throw new UntreatedException("untreated exception: " + e.getMessage());
		}
	}
	
	@Transactional
	public void delete(Long ProductionOrderId) {
		try {
			ProductionOrder productionOrder = productionOrderRepository.getOne(ProductionOrderId);
			repository.deleteByIdProductionOrder(productionOrder);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("ProductionOrder id:" + ProductionOrderId + " Not found");
		}catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}catch(Exception e) {
			throw new UntreatedException(e.getMessage());
		}
	}
}
