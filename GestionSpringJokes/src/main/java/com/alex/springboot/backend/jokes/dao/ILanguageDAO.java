package com.alex.springboot.backend.jokes.dao;

import org.springframework.data.repository.CrudRepository;

import com.alex.springboot.backend.jokes.entity.Language;

public interface ILanguageDAO extends CrudRepository<Language, Integer>{

}
