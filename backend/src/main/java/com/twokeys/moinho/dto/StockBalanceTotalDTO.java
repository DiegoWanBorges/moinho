package com.twokeys.moinho.dto;

public class StockBalanceTotalDTO extends StockBalanceDTO {
	private static final long serialVersionUID = 1L;
	
	private Double totalEntry;
	private Double totalOut;
	
	public StockBalanceTotalDTO() {
		super();
	}

	public Double getTotalEntry() {
		return totalEntry;
	}

	public void setTotalEntry(Double totalEntry) {
		this.totalEntry = totalEntry;
	}

	public Double getTotalOut() {
		return totalOut;
	}

	public void setTotalOut(Double totalOut) {
		this.totalOut = totalOut;
	}
	
	
}
