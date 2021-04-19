package com.twokeys.moinho.dto;

import java.io.Serializable;

import com.twokeys.moinho.entities.Employee;

public class EmployeeDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private SectorDTO sector;
	
	public EmployeeDTO() {
	}

	public EmployeeDTO(Long id, String name, SectorDTO sector) {
		this.id = id;
		this.name = name;
		this.sector = sector;
	}
	public EmployeeDTO(Employee entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.sector =  new SectorDTO(entity.getSector());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SectorDTO getSector() {
		return sector;
	}

	public void setSector(SectorDTO sector) {
		this.sector = sector;
	}
	
	
}
