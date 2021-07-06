package com.twokeys.moinho.dto;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.twokeys.moinho.entities.CostCalculation;
import com.twokeys.moinho.entities.enums.CostCalculationStatus;

public class CostCalculationDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", timezone="GMT-3", locale = "pt-BR")
	private Instant startDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", timezone="GMT-3", locale = "pt-BR" )
	private Instant endDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone="GMT-3")
	private LocalDate stockStartDate;
	private CostCalculationStatus status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone="GMT-3")
	private LocalDate referenceMonth;
	public CostCalculationDTO() {
	}

	public CostCalculationDTO(Long id, Instant startDate, Instant endDate, LocalDate stockStartDate,
							  CostCalculationStatus status, LocalDate referenceMonth) {
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.stockStartDate = stockStartDate;
		this.status = status;
		this.referenceMonth=referenceMonth;
	}
	public CostCalculationDTO(CostCalculation entity) {
		this.id = entity.getId();
		this.startDate = entity.getStartDate();
		this.endDate = entity.getEndDate();
		this.stockStartDate = entity.getStockStartDate();
		this.status = entity.getStatus();
		this.referenceMonth=entity.getReferenceMonth();
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

	public LocalDate getReferenceMonth() {
		return referenceMonth;
	}

	public void setReferenceMonth(LocalDate referenceMonth) {
		this.referenceMonth = referenceMonth;
	}
	
	
}
