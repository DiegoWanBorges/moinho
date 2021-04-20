package com.twokeys.moinho.dto;

import java.io.Serializable;

import com.twokeys.moinho.entities.ApportionmentType;
import com.twokeys.moinho.entities.enums.ProductionApportionmentType;

public class ApportionmentTypeDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private ProductionApportionmentType type;
	
	public ApportionmentTypeDTO() {
	}
	
	public ApportionmentTypeDTO(Long id, String name, ProductionApportionmentType type) {
		this.id = id;
		this.name = name;
		this.type = type;
	}
	public ApportionmentTypeDTO(ApportionmentType entity) {
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
