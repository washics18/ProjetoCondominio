package com.washics18.Condominio.main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import com.washics18.Condominio.permissao.Permissoes;
import com.washics18.Condominio.replace.Replace;


public class Leitor {

	public static void main(String[] args) {

		try {
			FileInputStream fstream = new FileInputStream("src/main/java/base.txt");
			BufferedReader brUsuario = new BufferedReader(new InputStreamReader(fstream, "UTF-8"));
			String strLine;
			String[] readLine;
			String emailSearched = JOptionPane.showInputDialog("Digite seu e-mail");
			List<String> tipoGrupoCondominio = new ArrayList<String>();
			HashMap<String, String> listaPermissoesCondominio = null;
			// Read File Line By Line
			while ((strLine = brUsuario.readLine()) != null) {

				// buscando linhas com usuários
				readLine = strLine.split(";");
				if (readLine[0].equals("Usuario") && readLine[1].equals(emailSearched)) {
					String campoGupoCondominio = readLine[2];
					campoGupoCondominio = campoGupoCondominio.replace("),(", ");(");
					campoGupoCondominio = campoGupoCondominio.replace("[", "");
					campoGupoCondominio = campoGupoCondominio.replace("]", "");
					tipoGrupoCondominio = Arrays.asList(campoGupoCondominio.split(";"));
				}
			}
			listaPermissoesCondominio = getListaPermissoes(tipoGrupoCondominio);
			
			for (Entry<String, String> permissoes : listaPermissoesCondominio.entrySet()) {
				System.out.println(permissoes.getKey() + ";" + "[" + Replace.changeSemiColonToComma(permissoes.getValue()) + "]");
			}
			
			fstream.close();

		} catch (Exception e) {
			System.out.println("deu erro" + e.getLocalizedMessage());
		}

	}

	private static HashMap<String, String> getListaPermissoes(List<String> pTipoGrupoCondominio) {
		ArrayList<HashMap<String, String>> listCondominioPermissoes = new ArrayList<HashMap<String, String>>();

		try {
			FileInputStream fstream = new FileInputStream("src/main/java/base.txt");
			BufferedReader brGrupo = new BufferedReader(new InputStreamReader(fstream, "UTF-8"));
			String strLine;
			String[] readLine;
			// Read File Line By Line
			while ((strLine = brGrupo.readLine()) != null) {

				// buscando linhas com usuários
				readLine = strLine.split(";");

				if (readLine[0].equals("Grupo")) {
					String readTpGrupoCondominio = "(" + readLine[1] + "," + readLine[2] + ")"; // Morador - 1 => (Morador,1)

					for (String el : pTipoGrupoCondominio) {
						if (readTpGrupoCondominio.equals(el)) {
							HashMap<String, String> hash = new HashMap<String, String>();
							hash.put(readLine[2], Replace.removeBrackets(readLine[3]));
							listCondominioPermissoes.add(hash);
						}
					}
				}
			}
			fstream.close();

		} catch (Exception e) {
			System.out.println("deu erro" + e.getLocalizedMessage());
		}
		
		return getPermissoesPorCondominio(listCondominioPermissoes);  
	}

	private static HashMap<String, String> getPermissoesPorCondominio(ArrayList<HashMap<String, String>> pListCondominioPermissoes) {
		HashMap<String, String> hash = new HashMap<String, String>();
		String condominio = "0";
		String condominioAtual = null;

		for (HashMap<String, String> el : pListCondominioPermissoes) {
			condominioAtual = el.keySet().toString(); 
			condominioAtual = Replace.removeBrackets(condominioAtual.toString());
			
			if (hash.containsKey(condominioAtual)) {
				String[] permissoes0 = hash.get(condominioAtual).split(";");
				String[] permissoes1 = el.get(condominioAtual).split(";");
				String permissoes = permissoesPorCondominio(permissoes0, permissoes1);
				hash.put(condominioAtual, permissoes); 

			} else {
			hash.put(condominioAtual, el.get(condominioAtual));
			}
			condominio = condominioAtual;
		}

		return hash;

	}
	
	private static String permissoesPorCondominio(String[] permissoes0, String[] permissoes1) {
		int permissoes0size = permissoes0.length;
		int permissoes1size = permissoes1.length;
		int cont = permissoes0size >= permissoes1size ? permissoes0size : permissoes1size; // pegar o que tiver mais elementos (mais módulos)
		List<String> a = new ArrayList<String>();
		List<String> b = new ArrayList<String>();

		for (int i = 0; i < cont; i++) {
			if (permissoes0size > i) {
				a.addAll(Arrays.asList(Replace.removeParentheses(permissoes0[i]).split(","))) ; // [Reserva, Escrita, Entregas, Nenhuma, Usuario, Leitura]
			} 
			if (permissoes1size > i) {
				b.addAll(Arrays.asList(Replace.removeParentheses(permissoes1[i]).split(","))) ;
			}
		}

		String[] modulos = { "Reservas", "Entregas", "Usuarios", "ModuloNovo"};
		String findPermissaoA = "";
		String findPermissaoB = "";
		String permissaoModuloFinal = "";
		ArrayList<String> todasAsPermissoes = new ArrayList<String>();
		for (String modulo : modulos) {
			String permissao = "(";
			if (a.indexOf(modulo) != -1) {
				findPermissaoA = a.get(a.indexOf(modulo) + 1); 
			} else {
				findPermissaoA = Permissoes.Nenhuma.label;
			}
			if (b.indexOf(modulo) != -1) {
				findPermissaoB = b.get(b.indexOf(modulo) + 1); 
			} else {
				findPermissaoB = Permissoes.Nenhuma.label;
			}

			if (Permissoes.getValue(findPermissaoA) >= Permissoes.getValue(findPermissaoB)) {
				permissaoModuloFinal = findPermissaoA;
			} else {
				permissaoModuloFinal = findPermissaoB;
			}
			
			permissao = permissao + modulo + "," + permissaoModuloFinal + ")";
			todasAsPermissoes.add(permissao);
		}
	
		return Replace.removeBrackets(Replace.changeCommaToSemiColon(todasAsPermissoes.toString()));
	}

	
		
}
