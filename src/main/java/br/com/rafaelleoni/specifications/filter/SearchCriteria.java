package br.com.rafaelleoni.specifications.filter;

import lombok.Data;

@Data
public class SearchCriteria {

	private String key;
    private FilterOperation operation;
    private Object value;
    private String predicate;
    
    public SearchCriteria(String key, String operation, Object value, String predicate) {
    	this.key = key;
    	this.operation = FilterOperation.fromValue(operation);
    	this.value = value;
    	this.predicate = predicate;
    }
    
    public boolean isOrPredicate() {
    	return predicate.equalsIgnoreCase("OR");
    }

}