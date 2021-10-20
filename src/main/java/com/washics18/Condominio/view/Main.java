package com.washics18.Condominio.view;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import com.washics18.Condominio.dao.Reader;
import com.washics18.Condominio.model.User;
import com.washics18.Condominio.model.Module;



public class Main {

	public static void main(String[] args) {
		User user = null;
		HashMap<String, ArrayList<Module>> userPermissionByCondo = null;
		try {
			user = Reader.getInstance().readUserByEmail(JOptionPane.showInputDialog("Digite seu e-mail"));
			userPermissionByCondo = Reader.getInstance().readPermissionsByUser(user);
			Reader.printResult(userPermissionByCondo);
		} catch (IOException e) {
			System.out.println("Erro ao consultar email");
		}
	}

}
