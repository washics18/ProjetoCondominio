package com.washics18.Condominio.permissao;


public enum Permissoes {
	
	Nenhuma("Nenhuma", 0), Leitura("Leitura", 1), Escrita("Escrita", 2), Exclusao("Exclusão", 3);

	public final String label;
	public final int value;

	private Permissoes(String label, int value) {
		this.label = label;
		this.value = value;
	}

	public static int getValue(String label) {
		for (Permissoes p : values()) {
			if (p.label.equals(label)) {
				return p.value;
			}
		}

		return 0;
	}

	public String getLabel() {
		return label;
	}

	public int getValue() {
		return value;
	}
	


}
