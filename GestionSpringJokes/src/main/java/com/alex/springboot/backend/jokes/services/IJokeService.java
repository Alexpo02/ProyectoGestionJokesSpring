package com.alex.springboot.backend.jokes.services;

import java.util.List;

import com.alex.springboot.backend.jokes.entity.Jokes;

public interface IJokeService {
	
	public List<Jokes> findAll();
	
	public Jokes findById(Integer id);
	
	public Jokes save(Jokes joke);
	
	public void deleteById(Integer id);
	
	public void delete(Jokes joke);
}
