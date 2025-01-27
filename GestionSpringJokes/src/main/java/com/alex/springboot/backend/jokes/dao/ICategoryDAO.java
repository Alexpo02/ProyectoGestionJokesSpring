package com.alex.springboot.backend.jokes.dao;

import org.springframework.data.repository.CrudRepository;

import com.alex.springboot.backend.jokes.entity.Categories;

public interface ICategoryDAO extends CrudRepository<Categories, Integer>{

}
