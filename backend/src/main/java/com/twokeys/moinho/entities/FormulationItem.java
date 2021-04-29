package com.twokeys.moinho.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.twokeys.moinho.entities.enums.FormulationItemType;
import com.twokeys.moinho.entities.pk.FormulationItemPK;

@Entity
@Table(name="tb_formulation_item")
public class FormulationItem implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private FormulationItemPK id = new FormulationItemPK();
	private Double quantity;
	private boolean round;
	private boolean rawMaterial;
	private FormulationItemType type;

	public FormulationItem() {
	}

	public FormulationItem(Formulation formulation,Product product,Double quantity, boolean round, 
						   boolean rawMaterial,FormulationItemType type) {
		this.id.setFormulation(formulation);
		this.id.setProduct(product);
		this.quantity=quantity;
		this.round = round;
		this.rawMaterial=rawMaterial;
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
	
	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public boolean getRound() {
		return round;
	}

	public void setRound(boolean round) {
		this.round = round;
	}
	
	public boolean getRawMaterial() {
		return rawMaterial;
	}

	public void setRawMaterial(boolean rawMaterial) {
		this.rawMaterial = rawMaterial;
	}

	public FormulationItemType getType() {
		return type;
	}

	public void setType(FormulationItemType type) {
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
		FormulationItem other = (FormulationItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
