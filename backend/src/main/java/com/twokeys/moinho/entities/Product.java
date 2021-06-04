package com.twokeys.moinho.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tb_product")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private String packaging;
	private Double netWeight;
	private Double grossWeight;
	private Integer validityDays;
	private Double costLastEntry = 0.0;
	private Double averageCost = 0.0;
	private Double rawMaterialConversion;
	private Double stockBalance = 0.0;
	
	@ManyToOne
	@JoinColumn(name="unity_id")
	private Unity unity;
	
	@ManyToOne
	@JoinColumn(name="group_id")
	private Group group;
	
	public Product() {
	}
	
	public Product(Long id, String name, String description, String packaging, Double netWeight, Double grossWeight,
				   Integer validityDays, Double costLastEntry, Double averageCost, Double rawMaterialConversion,
				   Double stockBalance, Unity unity, Group group) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.packaging = packaging;
		this.netWeight = netWeight;
		this.grossWeight = grossWeight;
		this.validityDays = validityDays;
		this.costLastEntry = costLastEntry;
		this.averageCost = averageCost;
		this.rawMaterialConversion = rawMaterialConversion;
		this.stockBalance = stockBalance;
		this.unity = unity;
		this.group = group;
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

	public Double getCostLastEntry() {
		return costLastEntry;
	}

	public void setCostLastEntry(Double costLastEntry) {
		this.costLastEntry = costLastEntry;
	}

	public Double getAverageCost() {
		return averageCost;
	}

	public void setAverageCost(Double averageCost) {
		this.averageCost = averageCost;
	}

	public Double getRawMaterialConversion() {
		return rawMaterialConversion;
	}

	public void setRawMaterialConversion(Double rawMaterialConversion) {
		this.rawMaterialConversion = rawMaterialConversion;
	}

	public Double getStockBalance() {
		return stockBalance;
	}

	public void setStockBalance(Double stockBalance) {
		this.stockBalance = stockBalance;
	}

	public Unity getUnity() {
		return unity;
	}

	public void setUnity(Unity unity) {
		this.unity = unity;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
