package com.twokeys.moinho.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.twokeys.moinho.entities.OperationalPayment;

public class OperationalPaymentDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone="GMT-3")
	private LocalDate date;
	private String description;
	private String documentNumber;
	private Double value;
	private OperationalCostTypeDTO operationalCostType;
	
	public OperationalPaymentDTO() {
	}
	
	public OperationalPaymentDTO(Long id, LocalDate date, String description, String documentNumber, Double value,
						   		 OperationalCostTypeDTO operationalCostType) {
		this.id = id;
		this.date = date;
		this.description = description;
		this.documentNumber = documentNumber;
		this.value = value;
		this.operationalCostType = operationalCostType;
	}

	public OperationalPaymentDTO(OperationalPayment entity) {
		this.id = entity.getId();
		this.date = entity.getDate();
		this.description=entity.getDescription();
		this.documentNumber=entity.getDocumentNumber();
		this.value = entity.getValue();
		this.operationalCostType = new OperationalCostTypeDTO(entity.getOperationalCostType());
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public OperationalCostTypeDTO getOperationalCostType() {
		return operationalCostType;
	}

	public void setOperationalCostType(OperationalCostTypeDTO operationalCostType) {
		this.operationalCostType = operationalCostType;
	}
}
