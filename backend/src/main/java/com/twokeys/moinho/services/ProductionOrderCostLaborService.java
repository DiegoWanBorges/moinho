package com.twokeys.moinho.services;



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

import com.twokeys.moinho.dto.PaymentCostLaborDTO;
import com.twokeys.moinho.dto.ProductionOrderCostLaborDTO;
import com.twokeys.moinho.entities.ProductionOrder;
import com.twokeys.moinho.entities.ProductionOrderCostLabor;
import com.twokeys.moinho.entities.Sector;
import com.twokeys.moinho.repositories.ProductionOrderCostLaborRepository;
import com.twokeys.moinho.repositories.ProductionOrderRepository;
import com.twokeys.moinho.services.exceptions.DatabaseException;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;
import com.twokeys.moinho.services.exceptions.UntreatedException;
import com.twokeys.moinho.util.Util;



@Service
public class ProductionOrderCostLaborService {
	protected final Log logger = LogFactory.getLog(getClass());
	@Autowired
	private ProductionOrderCostLaborRepository repository;
	 
	@Autowired
	private ProductionOrderRepository productionOrderRepository;
	@Autowired
	private LaborPaymentService laborPaymentService;
	
	@Transactional(readOnly = true)
	public List<ProductionOrderCostLaborDTO> findByIdProductionOrderId(Long id){
		List<ProductionOrderCostLabor> list = repository.findByIdProductionOrderId(id);
		return list.stream().map(x -> new ProductionOrderCostLaborDTO(x)).collect(Collectors.toList());
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
		}
	}
	
	@Transactional
	public void prorateCostLabor(Instant startDate, Instant endDate) {
		try {
			List<ProductionOrder> listProductionOrder = new ArrayList<>();
			Long productionDurationTotal;
			Double percent=0.0;
			Double proratedAmount;
			ProductionOrder productionOrder;
			ProductionOrderCostLabor productionOrderCostLabor;
			Double sum = 0.0;
			Double difSum = 0.0;
			
			/*Recupera os valores de por setor a ser rateado*/
			List<PaymentCostLaborDTO> paymentBySector= laborPaymentService.listCostLaborGroupBySector(Util.toLocalDate(startDate), Util.toLocalDate(endDate));	
			for(PaymentCostLaborDTO dto : paymentBySector) {
				productionDurationTotal =0L;
				listProductionOrder.clear();
							
				/*Recupera as Ordens de produções, vinculadas aos setores*/
				listProductionOrder = productionOrderRepository.listProductionOrderByStartDateAndFormulationSector(startDate, endDate,dto.getId());
				for(ProductionOrder item: listProductionOrder) {
					productionDurationTotal+=item.getProductionMinutes();
				}
				
				sum=0.0;
				for (int i = 0; i < listProductionOrder.size(); i++) {
					productionOrder=listProductionOrder.get(i);
					percent = Util.roundHalfUp2(Double.valueOf(productionOrder.getProductionMinutes()) / productionDurationTotal);
					proratedAmount=Util.roundHalfUp2(percent * dto.getTotal());
					
					/*DIFERANÇA DO RATEIO É LANÇADO NA ULTIMA OP*/
					sum+=proratedAmount;
					if ((i+1)==listProductionOrder.size()) {
						difSum=dto.getTotal()-sum;
						proratedAmount= Util.roundHalfUp2(proratedAmount+difSum);
					}
										
					productionOrderCostLabor = new ProductionOrderCostLabor();
					productionOrderCostLabor.setValue(proratedAmount);
					productionOrderCostLabor.setProductionOrder(productionOrder);
					productionOrderCostLabor.setSector(new Sector(dto.getId(),""));
					repository.saveAndFlush(productionOrderCostLabor);
				}
			}
		}catch(Exception e) {
			throw new UntreatedException("untreated exception: " + e.getMessage());
		}
	}	
}
