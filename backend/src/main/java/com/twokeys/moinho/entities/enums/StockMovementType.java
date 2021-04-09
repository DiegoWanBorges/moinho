package com.twokeys.moinho.entities.enums;

public enum StockMovementType {
	AJUSTE_ESTOQUE(0),
	PRODUCAO_ENTRADA(1),
	PRODUCAO_CONSUMO(2),
	PRODUCAO_RETORNO(3),
	VENDA(4),
	COMPRA(5);
	
	private int stockMovementType;
	
	private StockMovementType(int stockMovementType) {
		this.stockMovementType=stockMovementType;
	}
	
	public int getStockMovementType() {
		return stockMovementType;
	}
}
