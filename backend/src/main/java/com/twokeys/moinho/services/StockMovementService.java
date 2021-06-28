package com.twokeys.moinho.services;

import java.math.BigInteger;
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

import com.twokeys.moinho.dto.StockBalanceDTO;
import com.twokeys.moinho.dto.StockBalanceTotalDTO;
import com.twokeys.moinho.dto.StockMovementDTO;
import com.twokeys.moinho.entities.Product;
import com.twokeys.moinho.entities.StockMovement;
import com.twokeys.moinho.entities.enums.StockMovementType;
import com.twokeys.moinho.repositories.ProductRepository;
import com.twokeys.moinho.repositories.StockMovementRepository;
import com.twokeys.moinho.services.exceptions.BusinessRuleException;
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
	@Autowired
	private CostCalculationService costCalculationService;

	@Transactional(readOnly = true)
	public Page<StockMovementDTO> findByStartDateAndProduct(Long productId, LocalDate startDate, LocalDate endDate,
			PageRequest pageRequest) {

		Product product = (productId == 0) ? null : productRepository.getOne(productId);
		Page<StockMovement> page = repository.findByStartDateAndProduct(product, startDate, endDate, pageRequest);
		return page.map(x -> new StockMovementDTO(x));
	}

	@Transactional(readOnly = true)
	public StockBalanceDTO currentStockByProduct(Long productId) {
		try {
			Double balance = 0.0;
			Double financialStockBalance = 0.0;
			StockBalanceDTO dto = new StockBalanceDTO();
			List<Object[]> object = repository.currentStockByProduct(productId);
			dto.setId(productId);
			if (object.size() == 0) {
				dto.setAverageCost(0.0);
				dto.setBalance(0.0);
			} else {
				if (balance > 0) {
					balance = (Double) object.get(0)[3];
					financialStockBalance = (Double) object.get(0)[4];
					dto.setAverageCost(Util.roundHalfUp2(financialStockBalance / balance));
					dto.setBalance(Util.roundHalfUp2(balance));
				} else {
					dto.setAverageCost(0.0);
					dto.setBalance(0.0);
				}
			}
			return dto;
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("Entity not found");
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: " + productId);
		} catch (Exception e) {
			logger.info("currentStockByProduct");
			throw new UntreatedException(e.getMessage());
		}
	}

	@Transactional(readOnly = true)
	public List<StockBalanceDTO> stockByPreviousAndEqualDate(LocalDate date) {
		try {
			Double balance = 0.0;
			Double financialStockBalance = 0.0;
			List<StockBalanceDTO> list = new ArrayList<>();
			StockBalanceDTO dto;
			List<Object[]> object = repository.stockByPreviousAndEqualDate(date);
			BigInteger id;
			for (int i = 0; i < object.size(); i++) {
				dto = new StockBalanceDTO();
				id = (BigInteger) object.get(i)[0];
				dto.setId(id.longValue());
				dto.setName((String) object.get(i)[1]);
				dto.setUnity((String) object.get(i)[2]);
				balance = (Double) object.get(i)[3];
				financialStockBalance = (Double) object.get(i)[4];

				if (balance == 0) {
					dto.setAverageCost(0.0);
					dto.setBalance(0.0);
				} else {
					dto.setAverageCost(Util.roundHalfUp2(financialStockBalance / balance));
					dto.setBalance(Util.roundHalfUp2(balance));
				}
				list.add(dto);
			}

			return list;
		} catch (Exception e) {
			throw new UntreatedException(e.getMessage());
		}
	}

	@Transactional(readOnly = true)
	public List<StockBalanceDTO> stockByDateBetweenAndType(LocalDate startDate, LocalDate endDate,
			StockMovementType type) {
		try {
			Double balance = 0.0;
			Double financialStockBalance = 0.0;
			List<StockBalanceDTO> list = new ArrayList<>();
			StockBalanceDTO dto;
			List<Object[]> object = repository.stockByDateBetweenAndType(startDate, endDate, type);

			for (int i = 0; i < object.size(); i++) {
				dto = new StockBalanceDTO();
				dto.setId((Long) object.get(i)[0]);
				dto.setName((String) object.get(i)[1]);
				dto.setUnity((String) object.get(i)[2]);
				balance = (Double) object.get(i)[3];
				financialStockBalance = (Double) object.get(i)[4];

				if (balance == 0) {
					dto.setAverageCost(0.0);
					dto.setBalance(0.0);
				} else {
					dto.setAverageCost(Util.roundHalfUp2(financialStockBalance / balance));
					dto.setBalance(Util.roundHalfUp2(balance));
				}
				list.add(dto);
			}

			return list;
		} catch (Exception e) {
			throw new UntreatedException(e.getMessage());
		}
	}

	@Transactional(readOnly = true)
	public StockBalanceTotalDTO stockByDateBetweenAndProductId(LocalDate startDate, LocalDate endDate, Long productId) {
		try {
			Double balance = 0.0;
			Double financialStockBalance = 0.0;
			StockBalanceTotalDTO dto = new StockBalanceTotalDTO();
			List<Object[]> object = repository.stockByDateBetweenAndProductId(startDate, endDate, productId);
			
			dto.setId(productId);
			if (object.size() > 0) {
				
				dto.setName((String) object.get(0)[1]);
				dto.setUnity((String) object.get(0)[2]);
				balance = (Double) object.get(0)[3];
				financialStockBalance = (Double) object.get(0)[4];
				dto.setTotalEntry((Double) object.get(0)[5]);
				dto.setTotalOut((Double) object.get(0)[6]);
				if (balance == 0) {
					dto.setAverageCost(0.0);
					dto.setBalance(0.0);
				} else {
					dto.setAverageCost(Util.roundHalfUp2(financialStockBalance / balance));
					dto.setBalance(Util.roundHalfUp2(balance));
				}
			} else {
				dto.setAverageCost(0.0);
				dto.setBalance(0.0);
				dto.setTotalEntry(0.0);
				dto.setTotalOut(0.0);
			}

			return dto;
		} catch (Exception e) {
			throw new UntreatedException(e.getMessage());
		}
	}

	@Transactional(readOnly = true)
	public List<StockBalanceDTO> stockByCurrentAverageCost() {
		try {
			Double balance = 0.0;
			Double financialStockBalance = 0.0;
			List<StockBalanceDTO> list = new ArrayList<>();
			StockBalanceDTO dto;
			List<Object[]> object = repository.stockByCurrentAverageCost();

			for (int i = 0; i < object.size(); i++) {
				dto = new StockBalanceDTO();
				dto.setId((Long) object.get(i)[0]);
				dto.setName((String) object.get(i)[1]);
				dto.setUnity((String) object.get(i)[2]);
				balance = (Double) object.get(i)[3];
				financialStockBalance = (Double) object.get(i)[4];

				if (balance == 0) {
					dto.setAverageCost(0.0);
					dto.setBalance(0.0);
				} else {
					dto.setAverageCost(Util.roundHalfUp2(financialStockBalance / balance));
					dto.setBalance(Util.roundHalfUp2(balance));
				}
				list.add(dto);
			}

			return list;
		} catch (Exception e) {
			throw new UntreatedException(e.getMessage());
		}
	}

	@Transactional(readOnly = true)
	public StockBalanceDTO stockByProductAndPreviousAndEqualDate(Long productId, LocalDate date) {
		try {
			Double balance = 0.0;
			Double financialStockBalance = 0.0;
			StockBalanceDTO dto = new StockBalanceDTO();
			dto.setId(productId);

			List<Object[]> object = repository.stockByProductAndPreviousAndEqualDate(productId, date);

			if (object.size() == 0) {
				dto.setAverageCost(0.0);
				dto.setBalance(0.0);
			} else {
				dto.setName((String) object.get(0)[1]);
				dto.setUnity((String) object.get(0)[2]);
				balance = (Double) object.get(0)[3];
				financialStockBalance = (Double) object.get(0)[4];
				if (balance > 0) {
					dto.setAverageCost(Util.roundHalfUp2(financialStockBalance / balance));
					dto.setBalance(Util.roundHalfUp2(balance));
				} else {
					dto.setAverageCost(0.0);
					dto.setBalance(0.0);
				}

			}
			return dto;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: " + productId);
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new UntreatedException("Exceção não tratada em: stockByProductAndPreviousAndEqualDate");
		}
	}

	@Transactional(readOnly = true)
	public StockBalanceDTO stockByPreviousDateAndProductId(Long productId, LocalDate date) {
		try {
			Double balance = 0.0;
			Double financialStockBalance = 0.0;
			StockBalanceDTO dto = new StockBalanceDTO();
			dto.setId(productId);

			List<Object[]> object = repository.stockByPreviousDateAndProductId(productId, date);

			if (object.size() == 0) {
				dto.setAverageCost(0.0);
				dto.setBalance(0.0);
			} else {
				dto.setName((String) object.get(0)[1]);
				dto.setUnity((String) object.get(0)[2]);
				balance = (Double) object.get(0)[3];
				financialStockBalance = (Double) object.get(0)[4];
				if (balance > 0) {

					dto.setAverageCost(Util.roundHalfUp2(financialStockBalance / balance));
					dto.setBalance(Util.roundHalfUp2(balance));
				} else {
					dto.setAverageCost(0.0);
					dto.setBalance(0.0);
				}
			}
			return dto;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: " + productId);
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new UntreatedException("Exceção não tratada em: stockByProductAndPreviousAndEqualDate");
		}
	}

	@Transactional(readOnly = true)
	public List<StockMovementDTO> findByProduct(Long idProduct) {
		Product product = new Product();
		product.setId(idProduct);
		List<StockMovement> list = repository.findByProduct(product);
		return list.stream().map(x -> new StockMovementDTO(x)).collect(Collectors.toList());

	}

	@Transactional(readOnly = true)
	public StockMovementDTO findById(Long id) {
		Optional<StockMovement> obj = repository.findById(id);
		StockMovement entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new StockMovementDTO(entity);
	}

	@Transactional
	public StockMovementDTO insert(StockMovementDTO dto) {
		try {
			StockMovement entity = new StockMovement();
			Product product = new Product();
			convertToEntity(dto, entity);
			entity = repository.save(entity);

			costCalculationService.hasCostCalculation(dto.getDate());

			StockBalanceDTO stockBalance = currentStockByProduct(dto.getProduct().getId());

			/* Atualiza o custo e saldo de estoque */
			product = productRepository.findById(entity.getProduct().getId()).get();
			product.setStockBalance(stockBalance.getBalance());
			product.setAverageCost(stockBalance.getAverageCost());
			product.setCostLastEntry(entity.getCost());

			productRepository.save(product);

			return new StockMovementDTO(entity);
		} catch (BusinessRuleException e) {
			throw new BusinessRuleException(e.getMessage());
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found");
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Database integrity reference");
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new UntreatedException("Erro não tratado ao inserir estoque");
		}
	}

	@Transactional
	public StockMovementDTO update(Long id, StockMovementDTO dto) {
		try {
			StockMovement entity = repository.getOne(id);
			Product product = new Product();
			costCalculationService.hasCostCalculation(entity.getDate());
			convertToEntity(dto, entity);
			entity = repository.save(entity);

			StockBalanceDTO stockBalance = currentStockByProduct(dto.getProduct().getId());

			/* Atualiza o custo e saldo de estoque */
			product = productRepository.findById(entity.getProduct().getId()).get();
			product.setStockBalance(stockBalance.getBalance());
			product.setAverageCost(stockBalance.getAverageCost());
			product.setCostLastEntry(entity.getCost());
			productRepository.save(product);
			return new StockMovementDTO(entity);
		} catch (BusinessRuleException e) {
			throw new BusinessRuleException(e.getMessage());
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new UntreatedException("Erro não tratado ao atualizar estoque");
		}
	}

	@Transactional
	public void delete(Long id) {
		try {
			StockMovement entity = repository.getOne(id);
			costCalculationService.hasCostCalculation(entity.getDate());
			Product product = new Product();
			repository.deleteById(id);

			/* Atualiza o custo e saldo de estoque */
			product = productRepository.findById(entity.getProduct().getId()).get();
			StockBalanceDTO stockBalance = currentStockByProduct(product.getId());
			product.setStockBalance(stockBalance.getBalance());
			product.setAverageCost(stockBalance.getAverageCost());
			product.setCostLastEntry(entity.getCost());
			productRepository.save(product);
		} catch (BusinessRuleException e) {
			throw new BusinessRuleException(e.getMessage());
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new UntreatedException("Erro não tratado ao deletar estoque");
		}

	}

	@Transactional
	public void updateAverageCost() {
		try {
			List<StockBalanceDTO> list = stockByCurrentAverageCost();
			Product product;
			for (StockBalanceDTO dto : list) {
				product = productRepository.getOne(dto.getId());
				product.setStockBalance(dto.getBalance());
				product.setAverageCost(dto.getAverageCost());
				product.setCostLastEntry(dto.getAverageCost());
			}

		} catch (Exception e) {
			throw new UntreatedException("Falha não tratada ao atualizar custo médio geral");
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
