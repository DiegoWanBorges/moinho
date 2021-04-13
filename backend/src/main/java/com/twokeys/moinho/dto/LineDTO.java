package com.twokeys.moinho.dto;

import com.twokeys.moinho.entities.Line;

public class LineDTO {
	private Long id;
	private String name;
	
	public LineDTO() {
	}

	public LineDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public LineDTO(Line entity) {
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
