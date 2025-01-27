package com.alex.springboot.backend.jokes.services;

import java.util.List;

import com.alex.springboot.backend.jokes.entity.Categories;

public interface ICategoryService {
	public List<Categories> findAll();
	
	public Categories findById(Integer id);
	
	public Categories save(Categories category);
	
	public void deleteById(Integer id);
	
	public void delete(Categories category);
}
