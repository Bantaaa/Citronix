package org.banta.citronix.specification;

import org.banta.citronix.domain.Farm;
import org.banta.citronix.dto.farm.FarmSearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class FarmSpecifications {

    public static Specification<Farm> withCriteria(FarmSearchCriteria criteria) {
        return (Root<Farm> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Check if any criteria is provided
            boolean hasCriteria = false;

            // Name search (case-insensitive partial match)
            if (criteria.getName() != null && !criteria.getName().trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("name")),
                        "%" + criteria.getName().toLowerCase() + "%"));
                hasCriteria = true;
            }

            // Location search (case-insensitive partial match)
            if (criteria.getLocation() != null && !criteria.getLocation().trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("location")),
                        "%" + criteria.getLocation().toLowerCase() + "%"));
                hasCriteria = true;
            }

            // Area range search
            if (criteria.getMinArea() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("area"), criteria.getMinArea()));
                hasCriteria = true;
            }
            if (criteria.getMaxArea() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("area"), criteria.getMaxArea()));
                hasCriteria = true;
            }

            // Establishment date range search
            if (criteria.getEstablishedAfter() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dateEstablished"),
                        criteria.getEstablishedAfter()));
                hasCriteria = true;
            }
            if (criteria.getEstablishedBefore() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("dateEstablished"),
                        criteria.getEstablishedBefore()));
                hasCriteria = true;
            }

            // If no criteria provided, return no results
            if (!hasCriteria) {
                return cb.equal(cb.literal(1), 0); // Always false condition
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}