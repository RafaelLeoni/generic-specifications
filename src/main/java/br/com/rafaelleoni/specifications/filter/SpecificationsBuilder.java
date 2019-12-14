package br.com.rafaelleoni.specifications.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import br.com.rafaelleoni.specifications.exceptions.ParameterException;

@Component
public class SpecificationsBuilder<T> {
	
	@Autowired
	private SpecificationFactory<T> specificationFactory;
     
    private List<SearchCriteria> params;
    
    public SpecificationsBuilder<T> with(Map<String, Object> params) {
    	var query = params.entrySet().stream().map((entry) -> 
    		entry.getKey() + entry.getValue()
    	)
    	.collect(Collectors.joining(","));
    	return with(query);
    }
 
    public SpecificationsBuilder<T> with(String query) {
    	params = new ArrayList<SearchCriteria>();
    	Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(query + ",");
        while (matcher.find()) {
        	params.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
        }
        return this;
    }
 
	public Specification<T> build() {
        if (params.size() == 0) {
            throw new ParameterException();
        }
 
        var specs = params.stream()
          .map(specificationFactory::getByCriteria)
          .collect(Collectors.toList());
         
        var result = specs.get(0);
 
        for (int i = 1; i < specs.size(); i++) {
			result = Specification.where(result).and(specs.get(i));
			//TODO: Specification.where(result).or(specs.get(i)) :
        }
        return result;
    }
}