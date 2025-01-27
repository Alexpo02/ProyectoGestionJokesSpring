package com.alex.springboot.backend.jokes.dto;

import java.io.Serializable;

import com.alex.springboot.backend.jokes.entity.Flags;

public class FlagDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String flag;
	
	public FlagDTO() {
		super();
	}
	
	public FlagDTO(int id, String flag) {
		super();
		this.id = id;
		this.flag = flag;
	}
	
	public FlagDTO(Flags flag) {
		super();
		this.id = flag.getId();
		this.flag = flag.getFlag();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
