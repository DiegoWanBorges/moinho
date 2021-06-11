package com.twokeys.moinho.services;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ValidationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twokeys.moinho.dto.ProductDTO;
import com.twokeys.moinho.dto.ProductionOrderItemDTO;
import com.twokeys.moinho.dto.StockMovementDTO;
import com.twokeys.moinho.entities.Parameter;
import com.twokeys.moinho.entities.Product;
import com.twokeys.moinho.entities.ProductionOrder;
import com.twokeys.moinho.entities.ProductionOrderItem;
import com.twokeys.moinho.entities.StockMovement;
import com.twokeys.moinho.entities.enums.CostType;
import com.twokeys.moinho.entities.enums.ProductionOrderItemType;
import com.twokeys.moinho.entities.enums.ProductionOrderStatus;
import com.twokeys.moinho.entities.enums.StockMovementType;
import com.twokeys.moinho.entities.pk.ProductionOrderItemPK;
import com.twokeys.moinho.repositories.OccurrenceRepository;
import com.twokeys.moinho.repositories.ParameterRepository;
import com.twokeys.moinho.repositories.ProductRepository;
import com.twokeys.moinho.repositories.ProductionOrderItemRepository;
import com.twokeys.moinho.repositories.ProductionOrderRepository;
import com.twokeys.moinho.repositories.StockMovementRepository;
import com.twokeys.moinho.services.exceptions.StockMovementException;
import com.twokeys.moinho.services.exceptions.UntreatedException;
@Service
public class ProductionOrderItemService {
	protected final Log logger = LogFactory.getLog(getClass());
	@Autowired
	private ProductionOrderItemRepository repository;
	@Autowired 
	private ProductRepository productRepository; 
	@Autowired
	private ProductionOrderRepository productionOrderRepository;
	@Autowired
	private OccurrenceRepository occurrenceRepository;
	@Autowired
	private StockMovementService stockMovementService;
	@Autowired
	private StockMovementRepository stockMovementRepository;
	@Autowired
	private ParameterRepository parameterRepository;
	
	@Transactional(readOnly = true)
	public List<ProductionOrderItemDTO> findByDateAndProduct(Instant startDate,Instant endDate, Long productId) {
		List<ProductionOrderItem> list = repository.findByDateAndProduct(startDate, endDate, productId);
		return list.stream().map(x -> new ProductionOrderItemDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional
	public List<ProductionOrderItemDTO> insert(List<ProductionOrderItemDTO> dto) {
		try {
			Parameter parameter = parameterRepository.findById(1L).get();
			Product product;
			ProductionOrderItem entity = new ProductionOrderItem();
			List<ProductionOrderItem> entityList = new ArrayList<>();
			ProductionOrder productionOrder = productionOrderRepository.getOne(dto.get(0).getProductionOrderId());
			StockMovementDTO stockMovement;
			Integer serie = repository.findMaxSerie(productionOrder) + 1;
			
			if (productionOrder.getStatus() != ProductionOrderStatus.ABERTO) {
				throw new ValidationException("Produção com status " + productionOrder.getStatus() + " não pode ser alterada");
			}
			for(ProductionOrderItemDTO itemDto : dto){
				product = productRepository.findById(itemDto.getProduct().getId()).get();
				/*Validar estoque*/
				if (parameter.isProductionOrderWithoutStock()==false && itemDto.getType()!=ProductionOrderItemType.RETORNO  ) {
					if (product.getStockBalance() < itemDto.getQuantity()) {
					throw new StockMovementException("Product id " + itemDto.getProduct().getId() + " out of stock");
					}
				}
				entity = new ProductionOrderItem();
				itemDto.setSerie(serie);
				entity= convertToEntity(itemDto);
				
				/*Validar tipo de custo*/
				if (parameter.getTypeCostUsed()==CostType.CUSTO_MEDIO) {	
					entity.setCost(product.getAverageCost());
				}else {
					entity.setCost(product.getCostLastEntry());
				}
				entity.setProduct(product);
				/*Faz a inserção no estoque recuperando o ID*/
				stockMovement = new StockMovementDTO();
				stockMovement.setDate(LocalDate.now());
				if (itemDto.getType()==ProductionOrderItemType.RETORNO) {
					stockMovement.setCost(entity.getCost());
					stockMovement.setEntry(entity.getQuantity());
					stockMovement.setOut(0.0);
					stockMovement.setDescription("");
					stockMovement.setIdOrignMovement(entity.getProductionOrder().getId());
					stockMovement.setType(StockMovementType.PRODUCAO_RETORNO);
					stockMovement.setProduct(new ProductDTO(product));
					stockMovement=stockMovementService.insert(stockMovement);
				}else {
					stockMovement.setCost(entity.getCost());
					stockMovement.setOut(entity.getQuantity());
					stockMovement.setEntry(0.0);
					stockMovement.setDescription("");
					stockMovement.setIdOrignMovement(entity.getProductionOrder().getId());
					stockMovement.setType(StockMovementType.PRODUCAO_CONSUMO);
					stockMovement.setProduct(new ProductDTO(product));
					stockMovement=stockMovementService.insert(stockMovement);
				}
				entity.setStockId(stockMovement.getId());
				entityList.add(entity);
			}
			entityList = repository.saveAll(entityList);
			dto.clear();
			for(ProductionOrderItem item: entityList) {
				dto.add(new ProductionOrderItemDTO(item));
			}	
			return dto;
		}catch(StockMovementException e) {
			throw new StockMovementException(e.getMessage());
		}catch(Exception e) {
			throw new UntreatedException(e.getMessage());
		}
	}
	
	@Transactional
	public ProductionOrderItemDTO update(ProductionOrderItemDTO dto) {
		try {
			Parameter parameter = parameterRepository.getOne(1L);
			Product product = productRepository.getOne(dto.getProduct().getId());
			ProductionOrder productionOrder = productionOrderRepository.getOne(dto.getProductionOrderId());
			
			if (productionOrder.getStatus() != ProductionOrderStatus.ABERTO) {
				throw new ValidationException("Produção com status " + productionOrder.getStatus() + " não pode ser alterada");
			}
			
			ProductionOrderItemPK pk = new ProductionOrderItemPK();
			pk.setProduct(product);
			pk.setProductionOrder(productionOrder);
			pk.setSerie(dto.getSerie());
			ProductionOrderItem item = repository.getOne(pk);
			StockMovement stockMovement = stockMovementRepository.getOne(item.getStockId());
						
			
			/*Validar estoque*/
			if (parameter.isProductionOrderWithoutStock()==false && dto.getType()!=ProductionOrderItemType.RETORNO  ) {
				if ((product.getStockBalance()+item.getQuantity()) < dto.getQuantity()) {
				throw new StockMovementException("Product id " + dto.getProduct().getId() + " out of stock");
				}
			}
			
			/*ATUALIZA QUANTIDADE*/
			if (item.getType()==ProductionOrderItemType.RETORNO) {
				stockMovement.setEntry(dto.getQuantity());
			}else {
				stockMovement.setOut(dto.getQuantity());
			}
			item.setQuantity(dto.getQuantity());
			/*ATUALIZA O CUSTO*/
			if (dto.getCost() !=null) {
				item.setCost(dto.getCost());
				stockMovement.setCost(dto.getCost());
			}
			
			stockMovementService.update(item.getStockId(), new StockMovementDTO(stockMovement));
			return  new ProductionOrderItemDTO(repository.save(item));
		}catch(StockMovementException e) {
			throw new StockMovementException(e.getMessage());
		}catch(Exception e) {
			throw new UntreatedException(e.getMessage());
		}
	}
	
	@Transactional
	public void delete(Long productionOrderId,Long productId,Integer serie) {
		try {
			Product product = productRepository.getOne(productId);
			ProductionOrder productionOrder = productionOrderRepository.getOne(productionOrderId);
			ProductionOrderItemPK pk = new ProductionOrderItemPK();
			if (productionOrder.getStatus() != ProductionOrderStatus.ABERTO) {
				throw new ValidationException("Produção com status " + productionOrder.getStatus() + " não pode ser alterada");
			}
			
			pk.setProduct(product);
			pk.setProductionOrder(productionOrder);
			pk.setSerie(serie);
			ProductionOrderItem item = repository.getOne(pk);
			stockMovementService.delete(item.getStockId());
			repository.deleteById(pk);
			
		} catch (Exception e) {
			throw new UntreatedException(e.getMessage());
		}
		
	}
	
	public ProductionOrderItem convertToEntity(ProductionOrderItemDTO dto) {
			ProductionOrderItem entity = new ProductionOrderItem(); 
			entity.setSerie(dto.getSerie());
			entity.setProductionOrder(productionOrderRepository.getOne(dto.getProductionOrderId()));
			entity.setQuantity(dto.getQuantity());
			entity.setCost(dto.getCost());
			entity.setType(dto.getType());
			entity.setOccurrence(occurrenceRepository.getOne(dto.getOccurrence().getId()));
			entity.setRawMaterial(dto.getRawMaterial());
			return entity;
	}
}
