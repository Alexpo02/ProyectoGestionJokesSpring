package com.alex.springboot.backend.jokes.services;

import java.util.List;


import com.alex.springboot.backend.jokes.entity.Flags;

public interface IFlagService {
	public List<Flags> findAll();
	
	public Flags findById(Integer id);
	
	public Flags save(Flags cliente);
	
	public void deleteById(Integer id);
	
	public void delete(Flags cliente);
}
