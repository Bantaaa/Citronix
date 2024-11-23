package org.banta.citronix.repository;

import jakarta.validation.constraints.NotNull;
import org.banta.citronix.domain.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SaleRepository extends JpaRepository<Sale, UUID> {
    boolean existsByHarvestId(@NotNull(message = "Harvest ID is required") UUID harvestId);
}
