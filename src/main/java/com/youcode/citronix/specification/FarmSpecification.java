package com.youcode.citronix.specification;

import org.springframework.data.jpa.domain.Specification;

import com.youcode.citronix.dto.criteria.FarmSearchCriteria;
import com.youcode.citronix.entity.farm.Farm;

import jakarta.persistence.criteria.Predicate;

public class FarmSpecification {
    public static Specification<Farm> withCriteria(FarmSearchCriteria criteria) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (criteria.getName() != null) {
                predicate = cb.and(predicate, 
                    cb.like(cb.lower(root.get("name")), 
                    "%" + criteria.getName().toLowerCase() + "%"));
            }

            if (criteria.getLocation() != null) {
                predicate = cb.and(predicate, 
                    cb.like(cb.lower(root.get("location")), 
                    "%" + criteria.getLocation().toLowerCase() + "%"));
            }

            if (criteria.getMinArea() != null) {
                predicate = cb.and(predicate, 
                    cb.greaterThanOrEqualTo(root.get("area"), criteria.getMinArea()));
            }

            if (criteria.getMaxArea() != null) {
                predicate = cb.and(predicate, 
                    cb.lessThanOrEqualTo(root.get("area"), criteria.getMaxArea()));
            }

            if (criteria.getStartDate() != null) {
                predicate = cb.and(predicate, 
                    cb.greaterThanOrEqualTo(root.get("creationDate"), criteria.getStartDate()));
            }

            if (criteria.getEndDate() != null) {
                predicate = cb.and(predicate, 
                    cb.lessThanOrEqualTo(root.get("creationDate"), criteria.getEndDate()));
            }

            if (criteria.getIsDeleted() != null) {
                predicate = cb.and(predicate, 
                    cb.equal(root.get("isDeleted"), criteria.getIsDeleted()));
            }

            return predicate;
        };
    }
}