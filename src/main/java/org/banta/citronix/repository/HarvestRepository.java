package org.banta.citronix.repository;

import org.banta.citronix.domain.Harvest;
import org.banta.citronix.domain.enums.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HarvestRepository extends JpaRepository<Harvest, UUID> {
    @Query("SELECT CASE WHEN COUNT(h) > 0 THEN true ELSE false END FROM Harvest h JOIN h.harvestDetails hd WHERE hd.tree.id = :treeId AND h.season = :season AND YEAR(h.harvestDate) = :year")
    boolean isTreeHarvestedInSeason(@Param("treeId") UUID treeId, @Param("season") Season season, @Param("year") Integer year);

    @Query("SELECT CASE WHEN COUNT(h) > 0 THEN true ELSE false END FROM Harvest h JOIN h.harvestDetails hd JOIN hd.tree t JOIN t.field f WHERE f.id = :fieldId AND h.season = :season AND YEAR(h.harvestDate) = :year")
    boolean existsByFieldAndSeasonAndYear(@Param("fieldId") UUID fieldId, @Param("season") Season season, @Param("year") Integer year);
}