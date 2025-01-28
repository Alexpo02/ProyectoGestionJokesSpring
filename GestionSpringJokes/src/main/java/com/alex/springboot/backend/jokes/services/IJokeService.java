package com.alex.springboot.backend.jokes.services;

import java.util.List;

import com.alex.springboot.backend.jokes.entity.Jokes;
import com.alex.springboot.backend.jokes.entity.PrimeraVez;
import com.alex.springboot.backend.jokes.entity.Telefonos;

public interface IJokeService {
	
	public List<Jokes> findAll();
	
	public Jokes findById(Integer id);
	
	public Jokes save(Jokes joke);
	
	public void deleteById(Integer id);
	
	public void delete(Jokes joke);
	
	public PrimeraVez findPrimeraVezByJokeId(Integer jokeId);
	
	public PrimeraVez savePrimeraVez(PrimeraVez primeraVez);
	
	public List<Telefonos> findTelefonosByPrimeraVezId(Integer primeraVezId);
	
	public Telefonos saveTelefono(Telefonos telefono);
	
	public void deleteTelefonoById(Integer id);
	
	public void deletePrimeraVezById(Integer id);
	
	
}
