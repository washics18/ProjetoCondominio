package com.washics18.Condominio.replace;

public class Replace {
	
	public static String changeCommaToSemiColon (String value) {
		return value.replace("), (", ");(");
	}
	
	public static String changeSemiColonToComma (String value) {
		return value.replace(");(", "),(");
	}
	
	public static String removeParentheses(String value) {
		return value.replace("(", "").replace(")", "");
	}
	
	public static String removeBrackets(String value) {
		return value.replace("[", "").replace("]", "");
	}

}
