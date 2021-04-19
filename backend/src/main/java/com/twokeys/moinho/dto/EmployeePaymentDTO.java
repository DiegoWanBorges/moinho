package com.twokeys.moinho.dto;

import java.io.Serializable;
import java.time.Instant;

import com.twokeys.moinho.entities.EmployeePayment;

public class EmployeePaymentDTO implements Serializable {
		private static final long serialVersionUID = 1L;
		
		
		private Long id;
		private Instant date;
		private EmployeeDTO employee;
		private EmployeePaymentTypeDTO employeePaymentType;
		private Double paymentAmount;
		
		public EmployeePaymentDTO() {
		}

		public EmployeePaymentDTO(Long id, Instant date, EmployeeDTO employee,
								  EmployeePaymentTypeDTO employeePaymentType, Double paymentAmount) {
			this.id = id;
			this.date = date;
			this.employee = employee;
			this.employeePaymentType = employeePaymentType;
			this.paymentAmount = paymentAmount;
		}
		public EmployeePaymentDTO(EmployeePayment entity) {
			this.id = entity.getId();
			this.date = entity.getDate();
			this.employee = new EmployeeDTO(entity.getEmployee());
			this.employeePaymentType = new EmployeePaymentTypeDTO(entity.getEmployeePaymentType());
			this.paymentAmount = entity.getPaymentAmount();
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

		public EmployeeDTO getEmployee() {
			return employee;
		}

		public void setEmployee(EmployeeDTO employee) {
			this.employee = employee;
		}

		

		public EmployeePaymentTypeDTO getEmployeePaymentType() {
			return employeePaymentType;
		}

		public void setEmployeePaymentType(EmployeePaymentTypeDTO employeePaymentType) {
			this.employeePaymentType = employeePaymentType;
		}

		public Double getPaymentAmount() {
			return paymentAmount;
		}

		public void setPaymentAmount(Double paymentAmount) {
			this.paymentAmount = paymentAmount;
		}		
}
