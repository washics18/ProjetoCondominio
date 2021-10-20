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
		for (Permission permission : values()) {
			if (permission.label.equals(label)) {
				return permission.value;
			}
		}
		return 0;
	}
	
	public static Permission getByLabel(String label) {
		for (Permission permission : values()) {
			if (permission.label.equals(label)) {
				return permission;
			}
		}
		return null;
	}

	public Permission getValueByValue(int value) {
		for (Permission permission : values()) {
			if (permission.getValue() == value) {
				return permission;
			}
		}
		return null;
	}

	public static Permission getHighest(Permission permission1, Permission permission2) {
		if (permission1.getValue() >= permission2.getValue()) {
			return permission1;
		} else {
			return permission2;
		}
	}

}
