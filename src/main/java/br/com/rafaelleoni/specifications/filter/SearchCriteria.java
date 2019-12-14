package br.com.rafaelleoni.specifications.filter;

import lombok.Data;

@Data
public class SearchCriteria {

	private String key;
    private FilterOperation operation;
    private Object value;
    
    public SearchCriteria(String key, String operation, Object value) {
    	this.key = key;
    	this.operation = FilterOperation.fromValue(operation);
    	this.value = value;
    }
    
    public boolean isOrPredicate() {
    	return false;
    }

}