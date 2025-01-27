package com.alex.springboot.backend.jokes.services;

import java.util.List;

import com.alex.springboot.backend.jokes.entity.Types;

public interface ITypeService {
public List<Types> findAll();
	
	public Types findById(Integer id);
	
	public Types save(Types type);
	
	public void deleteById(Integer id);
	
	public void delete(Types type);
}
