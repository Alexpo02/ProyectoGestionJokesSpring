package com.alex.springboot.backend.jokes.dao;

import org.springframework.data.repository.CrudRepository;

import com.alex.springboot.backend.jokes.entity.Flags;

public interface IFlagDAO extends CrudRepository<Flags, Integer>{

}
