package com.alex.springboot.backend.jokes.dto;

import java.io.Serializable;

import com.alex.springboot.backend.jokes.entity.Language;

public class LanguageDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String code;
	private String language;
	
	public LanguageDTO() {
		super();
	}
	
	public LanguageDTO(int id, String code, String language) {
		super();
		this.id = id;
		this.code = code;
		this.language = language;
	}
	
	public LanguageDTO(Language language) {
		this.id = language.getId();
		this.code = language.getCode();
		this.language = language.getLanguage();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
