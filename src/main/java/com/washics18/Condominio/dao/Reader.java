package com.washics18.Condominio.dao;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;


import com.washics18.Condominio.model.Condo;
import com.washics18.Condominio.model.CondoModulePermission;
import com.washics18.Condominio.model.Module;
import com.washics18.Condominio.model.Permission;
import com.washics18.Condominio.model.RoleCondo;
import com.washics18.Condominio.model.User;
import com.washics18.Condominio.util.Constants;


public class Reader {

	private static Reader instance = null;

	private Reader() {
	}

	public static Reader getInstance() {
		if (instance == null) {
			instance = new Reader();
		}
		return instance;
	}

	public User readUserByEmail(String email) throws IOException {
		FileInputStream fstream = new FileInputStream("src/main/java/base.txt");
		BufferedReader brUser = new BufferedReader(new InputStreamReader(fstream, "UTF-8"));
		String strLine;
		String[] readLine;
		User user = new User(email);
		while ((strLine = brUser.readLine()) != null) {
			readLine = strLine.split(";");
			if (readLine[0].equals("Usuario") && readLine[1].equals(user.getEmail())) {
				user.setRolesCondo(readRolesCondo(readLine[2]));
			}
		}
		brUser.close();
		return user;
	}

	public HashMap<String, ArrayList<Module>> readPermissionsByUser(User user) throws IOException {
		FileInputStream fstream = new FileInputStream("src/main/java/base.txt");
		BufferedReader brGroup = new BufferedReader(new InputStreamReader(fstream, "UTF-8"));
		String strLine;
		String[] readLine;
		HashMap<String, ArrayList<Module>> condosModulePermission = new HashMap<String, ArrayList<Module>>();
		
		while ((strLine = brGroup.readLine()) != null) {
			readLine = strLine.split(Constants.FIELD_SEPARATOR);
			if (readLine[0].equals("Grupo")) {
				for (RoleCondo rolecondo : user.getRolesCondo()) {
					if (readLine[1].equals(rolecondo.getRole()) && readLine[2].equals(rolecondo.getCondo().getName())) {
						CondoModulePermission condoModulePermission = new CondoModulePermission();
						condoModulePermission.setCondo(rolecondo.getCondo());
						condoModulePermission.setModules(readModules(readLine[3]));
						if (condosModulePermission.containsKey(rolecondo.getCondo().getName())) {
							ArrayList<Module> modules = Module.getHigestCondoModuloPermission(
									condosModulePermission.get(rolecondo.getCondo().getName()), readModules(readLine[3]));
							condosModulePermission.put(rolecondo.getCondo().getName(), modules);
						} else {
							condosModulePermission.put(rolecondo.getCondo().getName(), readModules(readLine[3]));
						}
					}
				}
			}
		}
		brGroup.close();
		return condosModulePermission;
	}

	private static ArrayList<RoleCondo> readRolesCondo(String value) {
		ArrayList<RoleCondo> rolesCondo = new ArrayList<RoleCondo>();
		value = sanitazing(value);
		String[] aCondoGroups = value.split(Constants.FIELD_SEPARATOR);
		for (String condogrupo : aCondoGroups) {
			Condo condo = new Condo(condogrupo.split(Constants.VALUE_SEPARATOR)[1]);
			RoleCondo roleCondo = new RoleCondo();
			roleCondo.setCondo(condo);
			roleCondo.setRole(condogrupo.split(Constants.VALUE_SEPARATOR)[0]);
			rolesCondo.add(roleCondo);
		}
		return rolesCondo;
	}

	private ArrayList<Module> readModules(String value) {
		ArrayList<Module> modules = new ArrayList<Module>();
		value = sanitazing(value);
		String[] aModules = value.split(Constants.FIELD_SEPARATOR);
		for (String modulo : aModules) {
			Module module = new Module();
			module.setName(modulo.split(Constants.VALUE_SEPARATOR)[0]);
			module.setPermission(Permission.getByLabel(modulo.split(Constants.VALUE_SEPARATOR)[1]));
			modules.add(module);
		}
		return modules;
	}

	public static void printResult(HashMap<String, ArrayList<Module>> pUserPermissionByCondo) {
		for (Entry<String, ArrayList<Module>> condopermissao : pUserPermissionByCondo.entrySet()) {
			Iterator<Module> iterator = condopermissao.getValue().iterator();
			StringBuffer outPut = new StringBuffer();

			outPut.append(condopermissao.getKey());
			outPut.append(Constants.FIELD_SEPARATOR);
			outPut.append(Constants.OPEN_BRACKET);
			while (iterator.hasNext()) {
				Module modulo = iterator.next();
				outPut.append(Constants.OPEN_PARENTHESES);
				outPut.append(modulo.getName());
				outPut.append(Constants.COMMA);
				outPut.append(modulo.getPermission().label);
				outPut.append(Constants.CLOSE_PARENTHESES);
				if (iterator.hasNext()) {
					outPut.append(Constants.COMMA);
				}
			}
			outPut.append(Constants.CLOSE_BRACKET);
			System.out.println(outPut.toString());
		}
	}

	private static String sanitazing(String value) {
		value = value.replace("),(", ");("); 
		value = value.replace("[", "").replace("]", ""); 
		value = value.replace(",", Constants.VALUE_SEPARATOR); 
		value = value.replace("(", "").replace(")", ""); 
		return value;
	}

}
