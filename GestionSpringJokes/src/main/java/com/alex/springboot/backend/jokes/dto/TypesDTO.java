package com.alex.springboot.backend.jokes.dto;

import java.io.Serializable;

import com.alex.springboot.backend.jokes.entity.Types;

public class TypesDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String type;
	
	public TypesDTO() {
		super();
	}
	
	public TypesDTO(int id, String type) {
		super();
		this.id = id;
		this.type = type;
	}
	
	public TypesDTO(Types type) {
		super();
		this.id = type.getId();
		this.type = type.getType();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
