package com.twokeys.moinho.dto;

import java.io.Serializable;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.twokeys.moinho.entities.StockMovement;
import com.twokeys.moinho.entities.enums.StockMovementType;

public class StockMovementDTO   implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", timezone="GMT-3")
	private Instant date;
	private Double entry;
	private Double out;
	private Double cost;
	private String description;
	private StockMovementType type;
	private Long idOrignMovement;
	private ProductDTO product;
	
	public StockMovementDTO() {	
	}

	public StockMovementDTO(Long id, Instant date, Double entry, Double out, Double cost, String description,
							StockMovementType type, Long idOrignMovement, ProductDTO product) {
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
	public StockMovementDTO(StockMovement entity) {
		this.id = entity.getId();
		this.date = entity.getDate();
		this.entry = entity.getEntry();
		this.out = entity.getOut();
		this.cost = entity.getCost();
		this.description = entity.getDescription();
		this.type = entity.getType();
		this.idOrignMovement = entity.getIdOrignMovement();
		this.product = new ProductDTO(entity.getProduct());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
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

	public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
	}
	
	
}
