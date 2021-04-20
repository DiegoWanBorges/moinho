package com.twokeys.moinho.dto;

import java.io.Serializable;

import com.twokeys.moinho.entities.Occurrence;

public class OccurrenceDTO  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	
	public OccurrenceDTO() {
	}

	public OccurrenceDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public OccurrenceDTO(Occurrence entity) {
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
