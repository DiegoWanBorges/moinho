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

import com.twokeys.moinho.dto.ProductionOrderCostLaborDTO;
import com.twokeys.moinho.entities.ProductionOrder;
import com.twokeys.moinho.entities.ProductionOrderCostLabor;
import com.twokeys.moinho.entities.Sector;
import com.twokeys.moinho.repositories.LaborPaymentRepository;
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
	private LaborPaymentRepository laborPaymentRepository;
	
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
	public void laborPaymentApportionment(Instant startDate, Instant endDate) {
		try {
			List<ProductionOrder> listProductionOrder = new ArrayList<>();
			Long productionDurationTotal;
			Double percent=0.0;
			Double proratedAmount;

			ProductionOrderCostLabor productionOrderCostLabor;
			BigInteger sectorId;
			Long id;
			
			/*Recupera os valores de por setor a ser rateado*/
			List<Object[]> paymentBySector= laborPaymentRepository.listCostLaborGroupBySector(startDate, endDate);	
			for(Object[] inf : paymentBySector) {
				productionDurationTotal =0L;
				listProductionOrder.clear();
				 
				sectorId=(BigInteger) inf[0];
				id = sectorId.longValue();
				
				/*Recupera as Ordens de produções, vinculadas aos setores*/
				listProductionOrder = productionOrderRepository.listProductionOrderByStartDateAndFormulationSector(startDate, endDate,id);
				for(ProductionOrder item: listProductionOrder) {
					productionDurationTotal+=item.getProductionMinutes();
				}
				
				for(ProductionOrder item: listProductionOrder) {
					percent = Util.roundHalfUp2(Double.valueOf(item.getProductionMinutes()) / productionDurationTotal);
					proratedAmount=Util.roundHalfUp2(percent * (Double) inf[2]);
					productionOrderCostLabor = new ProductionOrderCostLabor();
					productionOrderCostLabor.setValue(proratedAmount);
					productionOrderCostLabor.setProductionOrder(item);
					productionOrderCostLabor.setSector(new Sector(id,""));
					repository.saveAndFlush(productionOrderCostLabor);
				}
			}
		}catch(Exception e) {
			throw new UntreatedException("untreated exception: " + e.getMessage());
		}
	}	
}
