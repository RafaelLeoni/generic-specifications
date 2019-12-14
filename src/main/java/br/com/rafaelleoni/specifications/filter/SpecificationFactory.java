package br.com.rafaelleoni.specifications.filter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class SpecificationFactory<T> {
	
	private Map<FilterOperation, Function<SearchCriteria, Specification<T>>> specs;
	
	@PostConstruct
	private void init() {
		specs = new HashMap<>();
		specs.put(FilterOperation.EQUAL, this::getEqualsSpecification);
		specs.put(FilterOperation.GREATER_THAN, this::getGreaterThanSpecification);
		specs.put(FilterOperation.LESS_THAN, this::getLessThanSpecification);
	}
	
	public Specification<T> getByCriteria(SearchCriteria criteria) {
		return specs.get(criteria.getOperation()).apply(criteria);
	}
	
	private Specification<T> getEqualsSpecification(SearchCriteria criteria) {
		return (root, query, builder) -> {
			if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(
                  root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
		};
	}
	
	private Specification<T> getGreaterThanSpecification(SearchCriteria criteria) {
		return (root, query, builder) -> {
			return builder
				.greaterThan(root.<String> get(criteria.getKey()), criteria.getValue().toString());
		};
	}
	
	private Specification<T> getLessThanSpecification(SearchCriteria criteria) {
		return (root, query, builder) -> {
			return builder
				.lessThan(root.<String> get(criteria.getKey()), criteria.getValue().toString());
		};
	}

}
