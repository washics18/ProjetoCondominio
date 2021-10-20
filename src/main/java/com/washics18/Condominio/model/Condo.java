package com.washics18.Condominio.model;

public class Condo {

	private String name;
	private int id;
	
	public Condo(String value) {
		this.name = value;
		this.id = Integer.valueOf(value);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
