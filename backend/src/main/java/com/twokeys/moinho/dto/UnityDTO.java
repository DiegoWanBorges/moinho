package com.twokeys.moinho.dto;

import java.io.Serializable;

import com.twokeys.moinho.entities.Unity;

public class UnityDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String description;
	
	public UnityDTO() {
	}

	public UnityDTO(String id, String description) {
		this.id = id;
		this.description = description;
	}
	public UnityDTO(Unity entity) {
		this.id = entity.getId();
		this.description = entity.getDescription();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
