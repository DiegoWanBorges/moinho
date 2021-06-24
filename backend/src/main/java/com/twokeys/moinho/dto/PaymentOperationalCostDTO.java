package com.twokeys.moinho.dto;

import com.twokeys.moinho.entities.enums.ProductionApportionmentType;

public class PaymentOperationalCostDTO {
	private Long id;
	private String name;
	private ProductionApportionmentType type;
	private Double total;
	
	public PaymentOperationalCostDTO() {
	}

	public PaymentOperationalCostDTO(Long id, String name, ProductionApportionmentType type, Double total) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.total = total;
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

	public ProductionApportionmentType getType() {
		return type;
	}

	public void setType(ProductionApportionmentType type) {
		this.type = type;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	
	
	
	
}
