package org.banta.citronix.repository;

import org.banta.citronix.domain.Harvest;
import org.banta.citronix.domain.enums.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HarvestRepository extends JpaRepository<Harvest, UUID> {
    @Query("SELECT COUNT(h) > 0 FROM Harvest h WHERE h.season = :season")
    boolean existsBySeasonAndYear(@Param("season") Season season);

    @Query("SELECT COUNT(hd) > 0 FROM HarvestDetail hd " +
            "WHERE hd.tree.id = :treeId AND hd.harvest.season = :season")
    boolean isTreeHarvestedInSeason(@Param("treeId") UUID treeId, @Param("season") Season season);
}