package com.twokeys.moinho.services;



import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

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
import com.twokeys.moinho.repositories.SectorRepository;
import com.twokeys.moinho.services.exceptions.DatabaseException;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;



@Service
public class ProductionOrderCostLaborService {
	protected final Log logger = LogFactory.getLog(getClass());
	@Autowired
	private ProductionOrderCostLaborRepository repository;
	@Autowired 
	private SectorRepository sectorRepository; 
	@Autowired
	private ProductionOrderRepository productionOrderRepository;
	@Autowired
	private LaborPaymentRepository laborPaymentRepository;
	
	@Transactional
	public ProductionOrderCostLaborDTO insert(ProductionOrderCostLaborDTO dto) {
		try {
			ProductionOrderCostLabor entity =new ProductionOrderCostLabor();
			convertToEntity(dto, entity);
			return new ProductionOrderCostLaborDTO(repository.save(entity));
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found");
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
		}
	}
	
	@Transactional
	public void LaborPaymentApportionment(Instant startDate, Instant endDate) {
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
				
				/*Recupera as Ordens de produções, vinculadas aos setores e realiza os rateios*/
				listProductionOrder = productionOrderRepository.listProductionOrderByStartDateAndFormulationSector(startDate, endDate,id);
				for(ProductionOrder item: listProductionOrder) {
					productionDurationTotal+=item.getStartDate().until(item.getEndDate(), ChronoUnit.MINUTES);
				}
				
				for(ProductionOrder item: listProductionOrder) {
					percent= Double.valueOf(item.getStartDate().until(item.getEndDate(), ChronoUnit.MINUTES)) / productionDurationTotal;
					percent= Double.valueOf(new BigDecimal(percent).setScale(2,RoundingMode.HALF_UP).toString());
					proratedAmount=Double.valueOf(new BigDecimal(percent * (Double) inf[2]).setScale(2,RoundingMode.HALF_UP).toString());
					productionOrderCostLabor = new ProductionOrderCostLabor();
					productionOrderCostLabor.setValue(proratedAmount);
					productionOrderCostLabor.setProductionOrder(item);
					productionOrderCostLabor.setSector(new Sector(id,""));
					repository.save(productionOrderCostLabor);
				}
			}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
	}
	
	public void convertToEntity(ProductionOrderCostLaborDTO dto, ProductionOrderCostLabor entity) {
		entity.setProductionOrder(productionOrderRepository.getOne(dto.getProductionOrderId()));
		entity.setSector(sectorRepository.getOne(dto.getSector().getId()));
		entity.setValue(dto.getValue());
	}
	
}