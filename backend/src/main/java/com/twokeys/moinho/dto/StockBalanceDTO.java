package com.twokeys.moinho.dto;

import java.io.Serializable;

public class StockBalanceDTO  implements Serializable {
	private static final long serialVersionUID = 1L; 
	
	private Long id;
	private String name;
	private String unity; 
	private Double balance;
	private Double averageCost;
	
	public StockBalanceDTO() {
	}

	public StockBalanceDTO(Long id, String name, String unity, Double balance, Double averageCost) {
		this.id = id;
		this.name = name;
		this.unity = unity;
		this.balance = balance;
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
