package com.washics18.Condominio.model;

import java.util.ArrayList;

public class CondoModulePermission {

	private Condo condo;
	private ArrayList<Module> modules;
	
	
	public Condo getCondo() {
		return condo;
	}
	public void setCondo(Condo condo) {
		this.condo = condo;
	}
	public ArrayList<Module> getModules() {
		return modules;
	}
	public void setModules(ArrayList<Module> modules) {
		this.modules = modules;
	}
	public void addMoudle(Module pModule) {
		this.modules.add(pModule);
	}
	public void removeMoudle(Module pModule) {
		this.modules.remove(pModule);
	}
	
}
