package com.alex.springboot.backend.jokes.services;

import java.util.List;

import com.alex.springboot.backend.jokes.entity.Language;

public interface ILanguageService {
	public List<Language> findAll();
	
	public Language findById(Integer id);
	
	public Language save(Language language);
	
	public void deleteById(Integer id);
	
	public void delete(Language language);
}
