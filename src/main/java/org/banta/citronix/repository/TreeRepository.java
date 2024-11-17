package org.banta.citronix.repository;

import org.banta.citronix.domain.Tree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TreeRepository extends JpaRepository<Tree, UUID> {
    @Query("SELECT COUNT(t) FROM Tree t WHERE t.field.id = :fieldId")
    public Long countByFieldId(@Param("fieldId") UUID fieldId);
}