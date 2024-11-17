package org.banta.citronix.repository;

import org.banta.citronix.domain.Tree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TreeRepository extends JpaRepository<Tree, UUID> {
    Long countByFieldId(UUID fieldId);
}
