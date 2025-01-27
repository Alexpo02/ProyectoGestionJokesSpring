package com.alex.springboot.backend.jokes.dao;

import org.springframework.data.repository.CrudRepository;

import com.alex.springboot.backend.jokes.entity.Types;

public interface ITypeDAO extends CrudRepository<Types, Integer>{

}
