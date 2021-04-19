package com.twokeys.moinho.dto;

import java.io.Serializable;

import com.twokeys.moinho.entities.Sector;

public class SectorDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	
	public SectorDTO() {	
	}

	public SectorDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	public SectorDTO(Sector entity) {
		this.id = entity.getId();
		this.name = entity.getName();
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
	
}
