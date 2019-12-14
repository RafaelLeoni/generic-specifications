package br.com.rafaelleoni.specifications.filter;

import java.util.stream.Stream;

import br.com.rafaelleoni.specifications.exceptions.NotImplementedException;

public enum FilterOperation {

	EQUAL(":"),
	NOT_EQUAL("!:"),
	GREATER_THAN(">"),
	GREATER_THAN_OR_EQUAL_TO(">="),
	LESS_THAN("<"),
	LESSTHAN_OR_EQUAL_TO("<="),
	IN("{}"),
	NOT_IN("!{}"),
	BETWEEN("<>"),
	CONTAINS("[]");
	
	private String value;

	private FilterOperation(String value) {
		this.value = value;
	}

	public static FilterOperation fromValue(String value) {
		return Stream.of(values())
			.filter(op -> String.valueOf(op.value).equalsIgnoreCase(value))
			.findFirst()
			.orElseThrow(NotImplementedException::new);
	}
	
	@Override
	public String toString() {
		return String.valueOf(value);
	}

}