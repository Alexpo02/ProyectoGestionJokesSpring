package com.alex.springboot.backend.jokes.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.alex.springboot.backend.jokes.entity.Telefonos;

public interface ITelefonosDAO extends CrudRepository<Telefonos, Integer> {
	List<Telefonos> findByPrimeraVezId(Integer primeraVezId);
}
