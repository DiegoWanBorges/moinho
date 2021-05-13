package com.twokeys.moinho.dto;

import java.io.Serializable;

import com.twokeys.moinho.entities.OperationalCostType;
import com.twokeys.moinho.entities.enums.ProductionApportionmentType;

public class OperationalCostTypeDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private ProductionApportionmentType type;
	
	public OperationalCostTypeDTO() {
	}
	
	public OperationalCostTypeDTO(Long id, String name, ProductionApportionmentType type) {
		this.id = id;
		this.name = name;
		this.type = type;
	}
	public OperationalCostTypeDTO(OperationalCostType entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.type = entity.getType();
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
	
	
	
}
