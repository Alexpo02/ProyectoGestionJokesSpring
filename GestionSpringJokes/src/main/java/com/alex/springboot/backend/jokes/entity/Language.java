package com.alex.springboot.backend.jokes.entity;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "language")
@NamedQueries({
    @NamedQuery(name = "Language.findAll",
        query = "SELECT l FROM Language l"),
    @NamedQuery(name = "Language.findByCode",
        query = "SELECT l FROM Language l WHERE l.code = :code")
})

public class Language implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String code;
	private String language;
	private Set<Jokes> jokeses = new HashSet<Jokes>(0);

	public Language() {
	}

	public Language(int id) {
		this.id = id;
	}

	public Language(int id, String code, String language, Set<Jokes> jokeses) {
		this.id = id;
		this.code = code;
		this.language = language;
		this.jokeses = jokeses;
	}

	@Id

	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "code", length = 2)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "language")
	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "language")
	public Set<Jokes> getJokeses() {
		return this.jokeses;
	}

	public void setJokeses(Set<Jokes> jokeses) {
		this.jokeses = jokeses;
	}
	
	@Override
	public String toString() {
		return "Language [id=" + id + ", code=" + code + ", language=" + language + "]";
	}

}
