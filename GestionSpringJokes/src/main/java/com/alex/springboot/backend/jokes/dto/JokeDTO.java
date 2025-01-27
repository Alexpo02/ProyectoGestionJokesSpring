package com.alex.springboot.backend.jokes.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.alex.springboot.backend.jokes.entity.Jokes;

public class JokeDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private CategoriesDTO categories = null;
    private LanguageDTO language = null;
    private TypesDTO types = null;
    private String text1;
    private String text2;
    private Set<FlagDTO> flagses = new HashSet<>();

    public JokeDTO() {
    }
    
    public JokeDTO(Jokes joke) {
    	this.id = joke.getId();
    	if (joke.getCategories() != null) {
            this.categories = new CategoriesDTO(joke.getCategories().getId(), joke.getCategories().getCategory());
        }

        if (joke.getLanguage() != null) {
            this.language = new LanguageDTO(joke.getLanguage().getId(), joke.getLanguage().getCode(), joke.getLanguage().getLanguage());
        }

        if (joke.getTypes() != null) {
            this.types = new TypesDTO(joke.getTypes().getId(), joke.getTypes().getType());
        }
    	this.text1 = joke.getText1();
    	this.text2 = joke.getText2();
    	joke.getFlagses().forEach(flag -> this.flagses.add(new FlagDTO(flag.getId(), flag.getFlag())));
    }

    public JokeDTO(Integer id, CategoriesDTO categories, LanguageDTO language, TypesDTO types, String text1, String text2, Set<FlagDTO> flagses) {
        this.id = id;
        this.categories = categories;
        this.language = language;
        this.types = types;
        this.text1 = text1;
        this.text2 = text2;
        this.flagses = flagses;
    }

    public Integer getId() { 
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CategoriesDTO getCategories() {
        return categories;
    }

    public void setCategories(CategoriesDTO categories) {
        this.categories = categories;
    }

    public LanguageDTO getLanguage() {
        return language;
    }

    public void setLanguage(LanguageDTO language) {
        this.language = language;
    }

    public TypesDTO getTypes() {
        return types;
    }

    public void setTypes(TypesDTO types) {
        this.types = types;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public Set<FlagDTO> getFlagses() {
        return flagses;
    }

    public void setFlagses(Set<FlagDTO> flagses) {
        this.flagses = flagses;
    }
}
