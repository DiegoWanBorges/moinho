package com.twokeys.moinho.dto;

import java.io.Serializable;

public class ProductionOrderProducedAverageCostDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String unity;
	private Double totalProduced;
	private Double averageCost;
	
	public ProductionOrderProducedAverageCostDTO() {
	}

	public ProductionOrderProducedAverageCostDTO(Long id, String name, String unity, Double totalProduced,
											     Double averageCost) {
		this.id = id;
		this.name = name;
		this.unity = unity;
		this.totalProduced = totalProduced;
		this.averageCost = averageCost;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnity() {
		return unity;
	}

	public void setUnity(String unity) {
		this.unity = unity;
	}

	public Double getTotalProduced() {
		return totalProduced;
	}

	public void setTotalProduced(Double totalProduced) {
		this.totalProduced = totalProduced;
	}

	public Double getAverageCost() {
		return averageCost;
	}

	public void setAverageCost(Double averageCost) {
		this.averageCost = averageCost;
	}

	
}
