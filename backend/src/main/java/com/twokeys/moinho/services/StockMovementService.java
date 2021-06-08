package com.twokeys.moinho.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.twokeys.moinho.dto.ProductDTO;
import com.twokeys.moinho.dto.StockBalanceDTO;
import com.twokeys.moinho.dto.StockMovementDTO;
import com.twokeys.moinho.entities.Product;
import com.twokeys.moinho.entities.StockMovement;
import com.twokeys.moinho.repositories.ProductRepository;
import com.twokeys.moinho.repositories.StockMovementRepository;
import com.twokeys.moinho.services.exceptions.DatabaseException;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;
import com.twokeys.moinho.services.exceptions.UntreatedException;



@Service
public class StockMovementService {
	protected final Log logger = LogFactory.getLog(getClass());
	@Autowired
	private StockMovementRepository repository;
	@Autowired
	private ProductRepository productRepository;
	
	@Transactional(readOnly=true)
	public Page<StockMovementDTO> findByStartDateAndProduct(Long productId,LocalDate startDate,LocalDate endDate,PageRequest pageRequest){
		
		Product product = (productId==0) ? null : productRepository.getOne(productId);
		Page<StockMovement> page = repository.findByStartDateAndProduct(product,startDate,endDate,pageRequest);
		return page.map(x -> new StockMovementDTO(x));
	}
	@Transactional(readOnly=true)
	public StockBalanceDTO currentStockByProduct(Long productId){
		try {
			Product product = productRepository.findById(productId).get();
			List<Object[]> object= repository.currentStockByProduct(product);
			StockBalanceDTO dto = new StockBalanceDTO();
			if (object.size() > 0) {
				dto.setProduct(new ProductDTO((Product)object.get(0)[0]));
				dto.setBalance(Double.valueOf(new BigDecimal((Double)object.get(0)[1]).setScale(2,RoundingMode.HALF_UP).toString()));
				dto.setAverageCost(Double.valueOf(new BigDecimal((Double)object.get(0)[2]).setScale(2,RoundingMode.HALF_UP).toString()));
			}
			return dto;
		}catch(ResourceNotFoundException e) {
			 throw new ResourceNotFoundException("Entity not found");
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: " + productId);
		} catch (Exception e) {
			throw new UntreatedException(e.getMessage());
		}
	}
	@Transactional(readOnly=true)
	public StockBalanceDTO stockByProductAndDatePrevious(Long productId, LocalDate date){
		try {
			Product product = productRepository.findById(productId).get();
			List<Object[]> object= repository.stockByProductAndDatePrevious(product,date);
			StockBalanceDTO dto = new StockBalanceDTO();
			if (object.size() > 0) {
				dto.setProduct(new ProductDTO((Product)object.get(0)[0]));
				dto.setBalance(Double.valueOf(new BigDecimal((Double)object.get(0)[1]).setScale(2,RoundingMode.HALF_UP).toString()));
				dto.setAverageCost(Double.valueOf(new BigDecimal((Double)object.get(0)[2]).setScale(2,RoundingMode.HALF_UP).toString()));
			}
			return dto;
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: " + productId);
		} catch (Exception e) {
			throw new UntreatedException(e.getMessage());
		}
	}
	
	@Transactional(readOnly=true)
	public List<StockMovementDTO> findByProduct(Long idProduct){
		Product product = new Product();
		product.setId(idProduct);
		List<StockMovement> list =  repository.findByProduct(product);
		return list.stream().map(x -> new StockMovementDTO(x)).collect(Collectors.toList());
		
	}
	@Transactional(readOnly=true)
	public StockMovementDTO findById(Long id){
		Optional<StockMovement> obj = repository.findById(id);
		StockMovement entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new StockMovementDTO(entity);
	}
	@Transactional
	public StockMovementDTO insert(StockMovementDTO dto) {
		try {
			StockMovement entity =new StockMovement();
			Product product = new Product();
			StockBalanceDTO stockBalance = currentStockByProduct(dto.getProduct().getId());
			convertToEntity(dto, entity);
			entity =repository.save(entity);
			
			/*Atualiza o custo e saldo de estoque*/
			product = productRepository.findById(entity.getProduct().getId()).get();
			if (stockBalance != null) {
				product.setStockBalance(stockBalance.getBalance());
				product.setAverageCost(stockBalance.getAverageCost());
			}else {
				product.setStockBalance(0.0);
				product.setAverageCost(0.0);
			}
			product.setCostLastEntry(entity.getCost());
						
			productRepository.save(product);

			return new StockMovementDTO(entity);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found");
		}catch (DataIntegrityViolationException e ) {
			throw new DatabaseException("Database integrity reference");
		}catch(Exception e) {
			throw new UntreatedException("Aqui "+e.getMessage());
		}
	}
	@Transactional
	public StockMovementDTO update(Long id, StockMovementDTO dto) {
		try {
			StockMovement entity = repository.getOne(id);
			Product product = new Product();
			StockBalanceDTO stockBalance = currentStockByProduct(dto.getProduct().getId());
			convertToEntity(dto, entity);
			entity = repository.save(entity);
			
			/*Atualiza o custo e saldo de estoque*/
			product = productRepository.findById(entity.getProduct().getId()).get();
			if (stockBalance != null) {
				product.setStockBalance(stockBalance.getBalance());
				product.setAverageCost(stockBalance.getAverageCost());
			}else {
				product.setStockBalance(0.0);
				product.setAverageCost(0.0);
			}
			product.setCostLastEntry(entity.getCost());
			productRepository.save(product);
			return new StockMovementDTO(entity);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		}catch(Exception e) {
			throw new UntreatedException(e.getMessage());
		}
	}
	@Transactional
	public void delete(Long id) {
		try {
			StockMovement entity = repository.getOne(id);
			Product product = new Product();
			repository.deleteById(id);
			
			/*Atualiza o custo e saldo de estoque*/
			product = productRepository.findById(entity.getProduct().getId()).get();
			StockBalanceDTO stockBalance = currentStockByProduct(product.getId());
			if (stockBalance != null) {
				product.setStockBalance(stockBalance.getBalance());
				product.setAverageCost(stockBalance.getAverageCost());
			}else {
				product.setStockBalance(0.0);
				product.setAverageCost(0.0);
			}
			product.setCostLastEntry(entity.getCost());
			productRepository.save(product);
			
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		}catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}catch(Exception e) {
			throw new UntreatedException(e.getMessage());
		}
		
	}
	public void convertToEntity(StockMovementDTO dto, StockMovement entity) {
		entity.setCost(dto.getCost());
		entity.setDescription(dto.getDescription());
		entity.setDate(dto.getDate());
		entity.setIdOrignMovement(dto.getIdOrignMovement());
		entity.setEntry(dto.getEntry());
		entity.setOut(dto.getOut());
		entity.setType(dto.getType());
		entity.setProduct(productRepository.getOne(dto.getProduct().getId()));
	}
}
