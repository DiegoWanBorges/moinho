package com.twokeys.moinho.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.twokeys.moinho.entities.ProductionOrder;
import com.twokeys.moinho.entities.enums.ProductionOrderStatus;

public class ProductionOrderDTO   implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", timezone="GMT-3")
	private Instant emission;
                                                                                                                                                                           
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", timezone="GMT-3")
	private Instant startDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", timezone="GMT-3")
	private Instant endDate;
	private Double expectedAmount;
	private String observation;
	private ProductionOrderStatus status;
	private FormulationDTO formulation;
	private List<ProductionOrderItemDTO> productionOrderItems = new ArrayList<>();
	private List<ProductionOrderProducedDTO> productionOrderProduceds = new ArrayList<>();
	private List<ProductionOrderCostLaborDTO> productionOrderCostLabor = new ArrayList<>();
	private List<ProductionOrderOperationalCostDTO> productionOrderOperationalCost = new ArrayList<>();
	
	public ProductionOrderDTO() {
	}

	public ProductionOrderDTO(Long id, Instant emission, Instant startDate, Instant endDate, Double expectedAmount,
							  String observation, ProductionOrderStatus status, FormulationDTO formulation) {
		this.id = id;
		this.emission = emission;
		this.startDate = startDate;
		this.endDate = endDate;
		this.expectedAmount = expectedAmount;
		this.observation = observation;
		this.status = status;
		this.formulation = formulation;
	}
	public ProductionOrderDTO(ProductionOrder entity) {
		this.id = entity.getId();
		this.emission = entity.getEmission();
		this.startDate = entity.getStartDate();
		this.endDate = entity.getEndDate();
		this.expectedAmount = entity.getExpectedAmount();
		this.observation = entity.getObservation();
		this.status = entity.getStatus();
		this.formulation = new FormulationDTO(entity.getFormulation());
		entity.getProductionOrderItems().forEach(items -> productionOrderItems.add(new ProductionOrderItemDTO(items)));
		entity.getProductionOrderProduceds().forEach(produced -> productionOrderProduceds.add(new ProductionOrderProducedDTO(produced)));
		entity.getProductionOrderCostLabor().forEach(costLabor -> productionOrderCostLabor.add(new ProductionOrderCostLaborDTO(costLabor)));
		entity.getProductionOrderOperationalCost().forEach(costOperational -> productionOrderOperationalCost.add(new ProductionOrderOperationalCostDTO(costOperational)));
	}
	public ProductionOrderDTO(ProductionOrder entity,List<ProductionOrderItemDTO> productionOrderItems) {
		this.id = entity.getId();
		this.emission = entity.getEmission();
		this.startDate = entity.getStartDate();
		this.endDate = entity.getEndDate();
		this.expectedAmount = entity.getExpectedAmount();
		this.observation = entity.getObservation();
		this.status = entity.getStatus();
		this.formulation = new FormulationDTO(entity.getFormulation());
		this.productionOrderItems.addAll(productionOrderItems);
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
	
	public FormulationDTO getFormulation() {
		return formulation;
	}

	public void setFormulation(FormulationDTO formulation) {
		this.formulation = formulation;
	}

	public List<ProductionOrderItemDTO> getProductionOrderItems() {
		return productionOrderItems;
	}

	public List<ProductionOrderProducedDTO> getProductionOrderProduceds() {
		return productionOrderProduceds;
	}

	public List<ProductionOrderCostLaborDTO> getProductionOrderCostLabor() {
		return productionOrderCostLabor;
	}

	public List<ProductionOrderOperationalCostDTO> getProductionOrderOperationalCost() {
		return productionOrderOperationalCost;
	}
	
	
}
