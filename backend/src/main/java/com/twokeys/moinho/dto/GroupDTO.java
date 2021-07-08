package com.twokeys.moinho.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.twokeys.moinho.entities.Group;

public class GroupDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotNull(message = "Não pode ser nulo")
	@NotBlank(message = "Não pode estar em branco")
	@Length(min=2,max=100, message = "Deve possuir entre 2 e 100 caracteres")
	private String name;
	
	public GroupDTO() {
	}

	public GroupDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public GroupDTO(Group entity) {
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
