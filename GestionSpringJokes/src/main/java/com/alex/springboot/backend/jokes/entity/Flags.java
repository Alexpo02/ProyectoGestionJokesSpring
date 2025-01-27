package com.alex.springboot.backend.jokes.entity;

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
import jakarta.persistence.Table;

@Entity
@Table(name = "flags")
public class Flags implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String flag;
	private Set<Jokes> jokeses = new HashSet<>(0);

	public Flags() {
	}

	public Flags(int id, String flag) {
		this.id = id;
		this.flag = flag;
	}

	public Flags(int id, String flag, Set<Jokes> jokeses) {
		this.id = id;
		this.flag = flag;
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

	@Column(name = "flag", nullable = false)
	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE })
	@JoinTable(
	    name = "jokes_flags",
	    joinColumns = @JoinColumn(name = "flag_id"),
	    inverseJoinColumns = @JoinColumn(name = "joke_id")
	)
	public Set<Jokes> getJokeses() {
	    return this.jokeses;
	}

	public void setJokeses(Set<Jokes> jokeses) {
	    this.jokeses = jokeses;
	}
	
	@Override
	public String toString() {
		return "Flags [id=" + id + ", flag=" + flag + "]";
	}

}
