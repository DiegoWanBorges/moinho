package com.twokeys.moinho.services;

import java.time.LocalDate;
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
import com.twokeys.moinho.util.Util;

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
			Double balance=0.0;
			Double financialStockBalance=0.0;

			Product product = productRepository.findById(productId).get();
			StockBalanceDTO dto = new StockBalanceDTO();
			dto.setProduct(new ProductDTO(product));
			
			List<Object[]> object= repository.currentStockByProduct(product.getId());
			balance=(Double)object.get(0)[0];
			financialStockBalance=(Double)object.get(0)[1];
			if(balance==0) {
				dto.setAverageCost(0.0);
				dto.setBalance(0.0);
			}else {
				dto.setAverageCost(Util.roundHalfUp2(financialStockBalance/balance));
				dto.setBalance(Util.roundHalfUp2(balance));
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
	public StockBalanceDTO stockByProductAndPreviousAndEqualDate(Long productId, LocalDate date){
		try {
			Double balance=0.0;
			Double financialStockBalance=0.0;

			Product product = productRepository.findById(productId).get();
			StockBalanceDTO dto = new StockBalanceDTO();
			dto.setProduct(new ProductDTO(product));
			
			List<Object[]> object= repository.stockByProductAndPreviousAndEqualDate(product,date);
			balance=(Double)object.get(0)[0];
			financialStockBalance=(Double)object.get(0)[1];
			if(balance==0) {
				dto.setAverageCost(0.0);
				dto.setBalance(0.0);
			}else {
				dto.setAverageCost(Util.roundHalfUp2(financialStockBalance/balance));
				dto.setBalance(Util.roundHalfUp2(balance));
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
			convertToEntity(dto, entity);
			entity =repository.save(entity);
			StockBalanceDTO stockBalance = currentStockByProduct(dto.getProduct().getId());
			
			/*Atualiza o custo e saldo de estoque*/
			product = productRepository.findById(entity.getProduct().getId()).get();
			product.setStockBalance(stockBalance.getBalance());
			product.setAverageCost(stockBalance.getAverageCost());
			product.setCostLastEntry(entity.getCost());
						
			productRepository.save(product);

			return new StockMovementDTO(entity);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found");
		}catch (DataIntegrityViolationException e ) {
			throw new DatabaseException("Database integrity reference");
		}catch(Exception e) {
			throw new UntreatedException("Aqui Mesmo"+e.getMessage());
		}
	}
	@Transactional
	public StockMovementDTO update(Long id, StockMovementDTO dto) {
		try {
			StockMovement entity = repository.getOne(id);
			Product product = new Product();
			
			convertToEntity(dto, entity);
			entity = repository.save(entity);
			StockBalanceDTO stockBalance = currentStockByProduct(dto.getProduct().getId());
			
			
			/*Atualiza o custo e saldo de estoque*/
			product = productRepository.findById(entity.getProduct().getId()).get();
			product.setStockBalance(stockBalance.getBalance());
			product.setAverageCost(stockBalance.getAverageCost());
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
			product.setStockBalance(stockBalance.getBalance());
			product.setAverageCost(stockBalance.getAverageCost());
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
