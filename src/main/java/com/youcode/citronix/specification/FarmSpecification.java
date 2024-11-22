package com.youcode.citronix.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.youcode.citronix.dto.criteria.FarmSearchCriteria;
import com.youcode.citronix.entity.farm.Farm;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

@Component
public class FarmSpecification {
    public Specification<Farm> withCriteria(FarmSearchCriteria criteria) {
        return (Root<Farm> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getName() != null && !criteria.getName().isEmpty()) {
                predicates.add(builder.like(builder.lower(root.get("name")), 
                    "%" + criteria.getName().toLowerCase() + "%"));
            }

            if (criteria.getMinArea() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("area"), criteria.getMinArea()));
            }

            if (criteria.getMaxArea() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("area"), criteria.getMaxArea()));
            }

            if (criteria.getLocation() != null && !criteria.getLocation().isEmpty()) {
                predicates.add(builder.like(builder.lower(root.get("location")), 
                    "%" + criteria.getLocation().toLowerCase() + "%"));
            }

            predicates.add(builder.equal(root.get("isDeleted"), false));

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}