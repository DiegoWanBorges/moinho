package com.twokeys.moinho.dto;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

import com.twokeys.moinho.entities.CostCalculation;
import com.twokeys.moinho.entities.enums.CostCalculationStatus;

public class CostCalculationDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Instant startDate;
	private Instant endDate;
	private LocalDate stockStartDate;
	private CostCalculationStatus status;
	
	public CostCalculationDTO() {
	}

	public CostCalculationDTO(Long id, Instant startDate, Instant endDate, LocalDate stockStartDate,
							  CostCalculationStatus status) {
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.stockStartDate = stockStartDate;
		this.status = status;
	}
	public CostCalculationDTO(CostCalculation entity) {
		this.id = entity.getId();
		this.startDate = entity.getStartDate();
		this.endDate = entity.getEndDate();
		this.stockStartDate = entity.getStockStartDate();
		this.status = entity.getStatus();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getStartDate() {
		return startDate;
	}

	public void setStartDate(Instant startDate) {
		this.startDate = startDate;
	}

	public Instant getEndDate() {
		return endDate;
	}

	public void setEndDate(Instant endDate) {
		this.endDate = endDate;
	}

	public LocalDate getStockStartDate() {
		return stockStartDate;
	}

	public void setStockStartDate(LocalDate stockStartDate) {
		this.stockStartDate = stockStartDate;
	}

	public CostCalculationStatus getStatus() {
		return status;
	}

	public void setStatus(CostCalculationStatus status) {
		this.status = status;
	}
	
	
	
	
	
}
