package com.twokeys.moinho.dto;

import java.io.Serializable;

public class PaymentCostLaborDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private Double total;
	
	public PaymentCostLaborDTO() {
	}

	public PaymentCostLaborDTO(Long id, String name, Double total) {
		this.id = id;
		this.name = name;
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

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	
	
}
