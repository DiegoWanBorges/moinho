package com.twokeys.moinho.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.twokeys.moinho.entities.enums.FormulationItemsType;
import com.twokeys.moinho.entities.pk.FormulationItemsPK;

@Entity
@Table(name="tb_formulation_items")
public class FormulationItems implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private FormulationItemsPK id;
	private Integer round;
	private FormulationItemsType type;
	
	public FormulationItems() {
	}

	public FormulationItems(Formulation formulation,Product product, Integer round, FormulationItemsType type) {
		this.id.setFormulation(formulation);
		this.id.setProduct(product);
		this.round = round;
		this.type = type;
	}
	
	public Formulation getFormulation() {
		return id.getFormulation();
	}
	public void setFormulation(Formulation formulation) {
		id.setFormulation(formulation);
	}
	public Product getProduct() {
		return id.getProduct();
	}
	public void setProduct(Product product) {
		id.setProduct(product);
	}
	
	public Integer getRound() {
		return round;
	}

	public void setRound(Integer round) {
		this.round = round;
	}

	public FormulationItemsType getType() {
		return type;
	}

	public void setType(FormulationItemsType type) {
		this.type = type;
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
		FormulationItems other = (FormulationItems) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
