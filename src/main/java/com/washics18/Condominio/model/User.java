package com.washics18.Condominio.model;

import java.util.ArrayList;

public class User {
	
	private String email;
	private ArrayList<RoleCondo> rolesCondo = new ArrayList<RoleCondo>();
	
	public User(String pEmail) {
		this.email = pEmail;
	}
	public ArrayList<RoleCondo> getRolesCondo() {
		return rolesCondo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String pEmail) {
		this.email = pEmail;
	}
	public void addRoleCondo (RoleCondo pRoleCondo) {
		this.rolesCondo.add(pRoleCondo);
	}
	public void setRolesCondo(ArrayList<RoleCondo> pRoleCondo) {
		this.rolesCondo = pRoleCondo;
	}

}
