package com.twokeys.moinho.entities;

import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.twokeys.moinho.entities.enums.ProductionOrderStatus;
@Entity
@Table(name="tb_production_order")
public class ProductionOrder  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant emission;
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant startDate;
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant endDate;
	private Double expectedAmount;
	private String observation;
	private ProductionOrderStatus status;
	
	@ManyToOne
	@JoinColumn(name="formulation_id")
	private Formulation formulation;
	
	@OneToMany(mappedBy = "id.productionOrder")
	private List<ProductionOrderItems> productionOrderItems = new ArrayList<>();
	
	@OneToMany(mappedBy = "id.productionOrder")
	private List<ProductionOrderProduced> productionOrderProduceds = new ArrayList<>();
	
	@OneToMany(mappedBy = "id.productionOrder")
	private List<ProductionOrderCostLabor> productionOrderCostLabor = new ArrayList<>();
	
	
	public ProductionOrder() {
	}

	public ProductionOrder(Long id, Instant emission, Instant startDate, Instant endDate, Double expectedAmount,
						   String observation, ProductionOrderStatus status, Formulation formulation) {
		this.id = id;
		this.emission = emission;
		this.startDate = startDate;
		this.endDate = endDate;
		this.expectedAmount = expectedAmount;
		this.observation = observation;
		this.status = status;
		this.formulation = formulation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getEmission() {
		return emission;
	}

	public void setEmission(Instant emission) {
		this.emission = emission;
	}

	public Instant getStartDate() {
		return startDate;
	}

	public void setStartDate(Instant startDate) {
		this.startDate = startDate;
	}

	public Instant getEndDate() {
		return endDate;
	}

	public void setEndDate(Instant endDate) {
		this.endDate = endDate;
	}

	public Double getExpectedAmount() {
		return expectedAmount;
	}

	public void setExpectedAmount(Double expectedAmount) {
		this.expectedAmount = expectedAmount;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public ProductionOrderStatus getStatus() {
		return status;
	}

	public void setStatus(ProductionOrderStatus status) {
		this.status = status;
	}
	public long  getProductionMinutes() {
		if (status==ProductionOrderStatus.ENCERRADO) {
		return	startDate.until(endDate, ChronoUnit.MINUTES);
		}
		return 0L;
	}
	public Formulation getFormulation() {
		return formulation;
	}

	public void setFormulation(Formulation formulation) {
		this.formulation = formulation;
	}
		
	public List<ProductionOrderItems> getProductionOrderItems() {
		return productionOrderItems;
	}
	
	public List<ProductionOrderProduced> getProductionOrderProduceds() {
		return productionOrderProduceds;
	}
	
	public List<ProductionOrderCostLabor> getProductionOrderCostLabor() {
		return productionOrderCostLabor;
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
		ProductionOrder other = (ProductionOrder) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
