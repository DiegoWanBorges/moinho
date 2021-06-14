package com.twokeys.moinho.dto;

import java.io.Serializable;

public class ProductionOrderProducedAverageCostDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private ProductDTO product;
	private Double totalProduced;
	private Double averageCost;
	
	public ProductionOrderProducedAverageCostDTO() {
	}

	public ProductionOrderProducedAverageCostDTO(ProductDTO product, Double totalProduced, Double averageCost) {
		this.product = product;
		this.totalProduced = totalProduced;
		this.averageCost = averageCost;
	}

	public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
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
