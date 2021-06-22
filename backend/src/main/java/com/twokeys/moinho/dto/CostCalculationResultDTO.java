package com.twokeys.moinho.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CostCalculationResultDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private CostCalculationDTO costCalculation;
	private List<ProductionOrderDTO> productionOrders = new ArrayList<>();
	private List<StockBalanceDTO> openingStockBalance = new ArrayList<>();
	private List<StockBalanceDTO> closingStockBalance = new ArrayList<>();
	private List<ProductionOrderProducedAverageCostDTO> productionOrderProducedAverageCosts = new ArrayList<>();
	private List<StockBalanceDTO> purchaseStockBalance = new ArrayList<>();
	private List<StockBalanceDTO> adjustmentStockBalance = new ArrayList<>();
	public CostCalculationResultDTO() {
	}

	public CostCalculationResultDTO(CostCalculationDTO costCalculation, List<ProductionOrderDTO> productionOrders,
									List<StockBalanceDTO> openingStockBalance, List<StockBalanceDTO> closingStockBalance,
									List<ProductionOrderProducedAverageCostDTO> productionOrderProducedAverageCosts,
									List<StockBalanceDTO> purchaseStockBalance,List<StockBalanceDTO> adjustmentStockBalance ) {
		this.costCalculation = costCalculation;
		this.productionOrders = productionOrders;
		this.openingStockBalance = openingStockBalance;
		this.closingStockBalance = closingStockBalance;
		this.productionOrderProducedAverageCosts=productionOrderProducedAverageCosts;
		this.purchaseStockBalance =purchaseStockBalance;
		this.adjustmentStockBalance=adjustmentStockBalance;
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

	public List<StockBalanceDTO> getOpeningStockBalance() {
		return openingStockBalance;
	}

	public List<StockBalanceDTO> getClosingStockBalance() {
		return closingStockBalance;
	}

	public List<ProductionOrderProducedAverageCostDTO> getProductionOrderProducedAverageCosts() {
		return productionOrderProducedAverageCosts;
	}

	public List<StockBalanceDTO> getPurchaseStockBalance() {
		return purchaseStockBalance;
	}

	public List<StockBalanceDTO> getAdjustmentStockBalance() {
		return adjustmentStockBalance;
	}
	
	
}
