package com.twokeys.moinho.entities;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.twokeys.moinho.entities.enums.CostCalculationStatus;

@Entity
@Table(name="tb_cost_calculation")
public class CostCalculation implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Instant startDate;
	private Instant endDate;
	private LocalDate stockStartDate;
	private CostCalculationStatus status;
	
	public CostCalculation() {
	}
	
	public CostCalculation(Long id, Instant startDate, Instant endDate, LocalDate stockStartDate,
			 			   CostCalculationStatus status) {
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.stockStartDate = stockStartDate;
		this.status=status;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CostCalculation other = (CostCalculation) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}