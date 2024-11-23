package org.banta.citronix.repository;

import org.banta.citronix.domain.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface FarmRepository extends JpaRepository<Farm, UUID>, JpaSpecificationExecutor<Farm> {
}
