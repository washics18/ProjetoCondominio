package com.washics18.Condominio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.washics18.Condominio.dao.Reader;
import com.washics18.Condominio.model.Module;
import com.washics18.Condominio.model.User;

@SpringBootApplication
public class CondominioApplication {

	public static void main(String[] args) {
		SpringApplication.run(CondominioApplication.class, args);
		
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
