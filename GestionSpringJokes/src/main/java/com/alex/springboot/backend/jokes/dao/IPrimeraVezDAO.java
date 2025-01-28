package com.alex.springboot.backend.jokes.dao;

import org.springframework.data.repository.CrudRepository;

import com.alex.springboot.backend.jokes.entity.PrimeraVez;

public interface IPrimeraVezDAO extends CrudRepository<PrimeraVez, Integer> {
	PrimeraVez findByJokeId(Integer jokeId);
}
