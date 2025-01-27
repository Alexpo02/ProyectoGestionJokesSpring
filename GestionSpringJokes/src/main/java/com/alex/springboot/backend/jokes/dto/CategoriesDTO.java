package com.alex.springboot.backend.jokes.dto;

import java.io.Serializable;

import com.alex.springboot.backend.jokes.entity.Categories;

public class CategoriesDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String category;
	
	public CategoriesDTO() {
		super();
	}
	
	public CategoriesDTO(int id, String category) {
		super();
		this.id = id;
		this.category = category;
	}
	
	public CategoriesDTO(Categories category) {
		this.id = category.getId();
		this.category = category.getCategory();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
