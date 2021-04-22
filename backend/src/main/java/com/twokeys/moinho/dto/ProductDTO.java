package com.twokeys.moinho.dto;

import java.io.Serializable;

import com.twokeys.moinho.entities.Product;

public class ProductDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String description;
	private String packaging;
	private Double netWeight;
	private Double grossWeight;
	private Integer validityDays;
	private UnityDTO unity;
	private GroupDTO group;
	
	public ProductDTO() {
	}

	public ProductDTO(Long id, String name, String description, String packaging, Double netWeight, Double grossWeight,
			Integer validityDays, UnityDTO unity, GroupDTO group) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.packaging = packaging;
		this.netWeight = netWeight;
		this.grossWeight = grossWeight;
		this.validityDays = validityDays;
		this.unity = unity;
		this.group = group;
	}
	public ProductDTO(Product entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.description = entity.getDescription();
		this.packaging = entity.getPackaging();
		this.netWeight = entity.getNetWeight();
		this.grossWeight = entity.getGrossWeight();
		this.validityDays = entity.getValidityDays();
		this.unity = new UnityDTO(entity.getUnity().getId(),entity.getUnity().getDescription()) ;
		this.group = new GroupDTO(entity.getGroup().getId(),entity.getGroup().getName());
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPackaging() {
		return packaging;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public Double getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}

	public Double getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(Double grossWeight) {
		this.grossWeight = grossWeight;
	}

	public Integer getValidityDays() {
		return validityDays;
	}

	public void setValidityDays(Integer validityDays) {
		this.validityDays = validityDays;
	}

	public UnityDTO getUnity() {
		return unity;
	}

	public void setUnity(UnityDTO unity) {
		this.unity = unity;
	}

	public GroupDTO getGroup() {
		return group;
	}

	public void setGroup(GroupDTO group) {
		this.group = group;
	}

	

}
