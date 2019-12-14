package br.com.rafaelleoni.specifications.filter;

import java.util.stream.Stream;

import br.com.rafaelleoni.specifications.exceptions.NotImplementedException;

public enum FilterOperation {

	EQUAL("eq"),
	NOT_EQUAL("neg"),
	GREATER_THAN("gt"),
	GREATER_THAN_OR_EQUAL_TO("gte"),
	LESS_THAN("lt"),
	LESSTHAN_OR_EQUAL_TO("lte"),
	IN("in"),
	NOT_IN("nin"),
	BETWEEN("btn"),
	CONTAINS("like");
	
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