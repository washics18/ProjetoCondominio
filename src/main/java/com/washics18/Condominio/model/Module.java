package com.washics18.Condominio.model;

import java.util.ArrayList;

public class Module {
	
	private final static String[] SYSTEM_MODULES = { "Reservas", "Entregas", "Usuarios", "ModuloNovo"};
	
	private String name;
	private Permission permission;

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Permission getPermission() {
		return permission;
	}
	public void setPermission(Permission permission) {
		this.permission = permission;
	}
	
	
	public static ArrayList<Module> getHigestCondoModuloPermission(ArrayList<Module> alModuleFirst, ArrayList<Module> alModuleSecond) {
		
		ArrayList<Module>  aModules = new ArrayList<Module>();
	
		for (String m : SYSTEM_MODULES) {
			Permission p1 = findModulePermission(m, alModuleFirst);
			Permission p2 = findModulePermission(m, alModuleSecond);
			Module module = new Module();
			module.setName(m);
			module.setPermission(Permission.getHighest(p1, p2));
			aModules.add(module);
		}
		
		return aModules;
	}
	
	private static Permission findModulePermission(String moduleName, ArrayList<Module> modules) {
		for (Module m : modules ) {
			if (m.name.equals(moduleName)){
				return m.permission;
			}
		}
		return Permission.None;
	}
}
