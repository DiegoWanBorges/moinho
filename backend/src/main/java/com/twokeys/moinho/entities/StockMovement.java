package com.twokeys.moinho.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.twokeys.moinho.entities.enums.StockMovementType;

@Entity
@Table(name="tb_stock_movement")
public class StockMovement  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate date;
	private Double entry;
	private Double out;
	private Double cost;
	private String description;
	private StockMovementType type;
	private Long idOrignMovement;
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;
	
	public StockMovement() {	
	}

	public StockMovement(Long id, LocalDate date, Double entry, Double out, Double cost, String description,
						 StockMovementType type, Long idOrignMovement, Product product) {
		this.id = id;
		this.date = date;
		this.entry = entry;
		this.out = out;
		this.cost = cost;
		this.description = description;
		this.type = type;
		this.idOrignMovement = idOrignMovement;
		this.product = product;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	

	public Double getEntry() {
		return entry;
	}

	public void setEntry(Double entry) {
		this.entry = entry;
	}

	public Double getOut() {
		return out;
	}

	public void setOut(Double out) {
		this.out = out;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public StockMovementType getType() {
		return type;
	}

	public void setType(StockMovementType type) {
		this.type = type;
	}

	public Long getIdOrignMovement() {
		return idOrignMovement;
	}

	public void setIdOrignMovement(Long idOrignMovement) {
		this.idOrignMovement = idOrignMovement;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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
		StockMovement other = (StockMovement) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
