package org.banta.citronix.specification;

import jakarta.persistence.criteria.Predicate;
import org.banta.citronix.domain.Farm;
import org.banta.citronix.dto.farm.FarmSearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class FarmSpecifications {

    public static Specification<Farm> withCriteria(FarmSearchCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getName() != null && !criteria.getName().trim().isEmpty()) {
                predicates.add(cb.like(root.get("name"), "%" + criteria.getName() + "%"));
            }

            if (criteria.getLocation() != null && !criteria.getLocation().trim().isEmpty()) {
                predicates.add(cb.like(root.get("location"), "%" + criteria.getLocation() + "%"));
            }

            if (criteria.getMinArea() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("area"), criteria.getMinArea()));
            }

            return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}