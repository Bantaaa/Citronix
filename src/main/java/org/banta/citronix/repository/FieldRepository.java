package org.banta.citronix.repository;

import org.banta.citronix.domain.Farm;
import org.banta.citronix.domain.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FieldRepository extends JpaRepository<Field, UUID> {

    @Query("SELECT f FROM Field f WHERE f.farm.id = :farmId")
    List<Field> findByFarmId(@Param("farmId") UUID farmId);

    @Query("SELECT SUM(f.area) FROM Field f WHERE f.farm.id = :farmId")
    Double sumAreaByFarmId(@Param("farmId") UUID farmId);
    Long countByFarmId(UUID farmId);

    @Query("SELECT DISTINCT f.farm FROM Field f JOIN f.farm farm WHERE f.id = :fieldId")
    Optional<Farm> findFarmByFieldId(@Param("fieldId") UUID fieldId);
}