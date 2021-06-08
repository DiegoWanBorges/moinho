package com.twokeys.moinho.dto;

import java.io.Serializable;

public class StockBalanceDTO  implements Serializable {
	private static final long serialVersionUID = 1L; 
	
	private ProductDTO product;
	private Double balance;
	private Double averageCost;
	
	public StockBalanceDTO() {
	}

	public StockBalanceDTO(ProductDTO product, Double balance, Double averageCost) {
		this.product = product;
		this.balance = balance;
		this.averageCost = averageCost;
	}

	public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getAverageCost() {
		return averageCost;
	}

	public void setAverageCost(Double averageCost) {
		this.averageCost = averageCost;
	}
	
	
	
	
}
