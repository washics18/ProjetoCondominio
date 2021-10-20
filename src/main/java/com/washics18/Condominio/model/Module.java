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
	
		for (String modulo : SYSTEM_MODULES) {
			Permission permission1 = findModulePermission(modulo, alModuleFirst);
			Permission permission2 = findModulePermission(modulo, alModuleSecond);
			Module module = new Module();
			module.setName(modulo);
			module.setPermission(Permission.getHighest(permission1, permission2));
			aModules.add(module);
		}
		
		return aModules;
	}
	
	private static Permission findModulePermission(String moduleName, ArrayList<Module> modules) {
		for (Module modulo : modules ) {
			if (modulo.name.equals(moduleName)){
				return modulo.permission;
			}
		}
		return Permission.None;
	}
}
