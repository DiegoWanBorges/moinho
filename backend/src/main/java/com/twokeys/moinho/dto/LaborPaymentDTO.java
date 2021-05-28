package com.twokeys.moinho.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.twokeys.moinho.entities.LaborPayment;

public class LaborPaymentDTO implements Serializable {
		private static final long serialVersionUID = 1L;
				
		private Long id;
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone="GMT-3")
		private LocalDate date;
		private String description;
		private String documentNumber;
		private Double value;
		private EmployeeDTO employee;
		private LaborCostTypeDTO laborCostType;
		
		public LaborPaymentDTO() {
		}
		
		public LaborPaymentDTO(Long id, LocalDate date, String description, String documentNumber, Double value,
				EmployeeDTO employee, LaborCostTypeDTO laborCostType) {
			this.id = id;
			this.date = date;
			this.description = description;
			this.documentNumber = documentNumber;
			this.value = value;
			this.employee = employee;
			this.laborCostType = laborCostType;
		}

		public LaborPaymentDTO(LaborPayment entity) {
			id = entity.getId();
			date = entity.getDate();
			description=entity.getDescription();
			documentNumber=entity.getDocumentNumber();
			value = entity.getValue();
			employee = new EmployeeDTO(entity.getEmployee());
			laborCostType = new LaborCostTypeDTO(entity.getLaborCostType());
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

		public EmployeeDTO getEmployee() {
			return employee;
		}

		public void setEmployee(EmployeeDTO employee) {
			this.employee = employee;
		}

		public LaborCostTypeDTO getLaborCostType() {
			return laborCostType;
		}

		public void setLaborCostType(LaborCostTypeDTO laborCostType) {
			this.laborCostType = laborCostType;
		}
		
}
