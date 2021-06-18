package com.twokeys.moinho.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CostCalculationResultDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private CostCalculationDTO costCalculation;
	private List<ProductionOrderDTO> productionOrders = new ArrayList<>();
	private List<StockBalanceDTO> stockBalances = new ArrayList<>();
	
	public CostCalculationResultDTO() {
	}

	public CostCalculationResultDTO(CostCalculationDTO costCalculation, List<ProductionOrderDTO> productionOrders,
								 List<StockBalanceDTO> stockBalances) {
		this.costCalculation = costCalculation;
		this.productionOrders.addAll(productionOrders);
		this.stockBalances.addAll(stockBalances);
	}

	public CostCalculationDTO getCostCalculation() {
		return costCalculation;
	}

	public void setCostCalculation(CostCalculationDTO costCalculation) {
		this.costCalculation = costCalculation;
	}

	public List<ProductionOrderDTO> getProductionOrders() {
		return productionOrders;
	}

	public List<StockBalanceDTO> getStockBalances() {
		return stockBalances;
	}
}
