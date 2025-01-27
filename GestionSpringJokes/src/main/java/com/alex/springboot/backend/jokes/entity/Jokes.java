package com.alex.springboot.backend.jokes.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "jokes")
public class Jokes implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Categories categories;
	private Language language;
	private Types types;
	private String text1;
	private String text2;
	private Set<Flags> flagses = new HashSet<Flags>(0);

	public Jokes() {
	}

	public Jokes(int id) {
		this.id = id;
	}

	public Jokes(int id, Categories categories, Language language, Types types, String text1, String text2,
			Set<Flags> flagses) {
		this.id = id;
		this.categories = categories;
		this.language = language;
		this.types = types;
		this.text1 = text1;
		this.text2 = text2;
		this.flagses = flagses;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	public Categories getCategories() {
		return this.categories;
	}

	public void setCategories(Categories categories) {
		this.categories = categories;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "language_id")
	public Language getLanguage() {
		return this.language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type_id")
	public Types getTypes() {
		return this.types;
	}

	public void setTypes(Types types) {
		this.types = types;
	}

	@Column(name = "text1", length = 1000)
	public String getText1() {
		return this.text1;
	}

	public void setText1(String text1) {
		this.text1 = text1;
	}

	@Column(name = "text2", length = 1000)
	public String getText2() {
		return this.text2;
	}

	public void setText2(String text2) {
		this.text2 = text2;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST})
	@JoinTable(name = "jokes_flags", joinColumns = {
			@JoinColumn(name = "joke_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "flag_id", referencedColumnName = "id") })
	public Set<Flags> getFlagses() {
		return this.flagses;
	}

	public void setFlagses(Set<Flags> flagses) {
		this.flagses = flagses;
	}
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("Jokes {\n")
	      .append("  id: ").append(id).append(",\n")
	      .append("  categories: ").append(categories != null ? categories.toString() : "null").append(",\n")
	      .append("  language: ").append(language != null ? language.toString() : "null").append(",\n")
	      .append("  types: ").append(types != null ? types.toString() : "null").append(",\n")
	      .append("  text1: '").append(text1).append("',\n")
	      .append("  text2: '").append(text2).append("',\n")
	      .append("  flagses: ").append(formatFlagses()).append("\n")
	      .append("}");
	    return sb.toString();
	}

	private String formatFlagses() {
	    if (flagses == null || flagses.isEmpty()) {
	        return "[]";
	    }
	    StringBuilder sb = new StringBuilder("[");
	    for (Flags flag : flagses) {
	        sb.append("\n    ").append(flag != null ? flag.toString() : "null");
	    }
	    sb.append("\n  ]");
	    return sb.toString();
	}



}
