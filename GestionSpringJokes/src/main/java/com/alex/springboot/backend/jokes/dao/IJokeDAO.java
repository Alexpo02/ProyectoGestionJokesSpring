package com.alex.springboot.backend.jokes.dao;

import org.springframework.data.repository.CrudRepository;

import com.alex.springboot.backend.jokes.entity.Jokes;

public interface IJokeDAO extends CrudRepository<Jokes, Integer> {

}
