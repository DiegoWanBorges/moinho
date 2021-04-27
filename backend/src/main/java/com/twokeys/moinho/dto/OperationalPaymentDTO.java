package com.twokeys.moinho.dto;

import java.io.Serializable;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.twokeys.moinho.entities.OperationalPayment;

public class OperationalPaymentDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", timezone="GMT-3")
	private Instant date;
	private String description;
	private String documentNumber;
	private Double value;
	private ApportionmentTypeDTO apportionmentType;
	
	public OperationalPaymentDTO() {
	}
	
	public OperationalPaymentDTO(Long id, Instant date, String description, String documentNumber, Double value,
						   ApportionmentTypeDTO apportionmentType) {
		this.id = id;
		this.date = date;
		this.description = description;
		this.documentNumber = documentNumber;
		this.value = value;
		this.apportionmentType = apportionmentType;
	}

	public OperationalPaymentDTO(OperationalPayment entity) {
		this.id = entity.getId();
		this.date = entity.getDate();
		this.description=entity.getDescription();
		this.documentNumber=entity.getDocumentNumber();
		this.value = entity.getValue();
		this.apportionmentType = new ApportionmentTypeDTO(entity.getApportionmentType());
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

	public ApportionmentTypeDTO getApportionmentType() {
		return apportionmentType;
	}

	public void setApportionmentType(ApportionmentTypeDTO apportionmentType) {
		this.apportionmentType = apportionmentType;
	}
	
	

}
