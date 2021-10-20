package com.washics18.Condominio.model;

public enum Permission {

	None("Nenhuma", 0), Read("Leitura", 1), Write("Escrita", 2), Exclude("Exclusão", 3);

	public final String label;
	public final int value;

	public String getLabel() {
		return label;
	}

	public int getValue() {
		return value;
	}

	private Permission(String label, int value) {
		this.label = label;
		this.value = value;
	}

	public static int getValueByLabel(String label) {
		for (Permission p : values()) {
			if (p.label.equals(label)) {
				return p.value;
			}
		}
		return 0;
	}
	
	public static Permission getByLabel(String label) {
		for (Permission p : values()) {
			if (p.label.equals(label)) {
				return p;
			}
		}
		return null;
	}

	public Permission getValueByValue(int value) {
		for (Permission p : values()) {
			if (p.getValue() == value) {
				return p;
			}
		}
		return null;
	}

	public static Permission getHighest(Permission p1, Permission p2) {
		if (p1.getValue() >= p2.getValue()) {
			return p1;
		} else {
			return p2;
		}
	}

}
