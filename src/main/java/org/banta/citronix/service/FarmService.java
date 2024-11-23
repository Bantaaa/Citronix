package org.banta.citronix.service;

import org.banta.citronix.dto.farm.FarmDTO;
import org.banta.citronix.dto.farm.FarmSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface FarmService {
    FarmDTO save(FarmDTO farmDTO);
    FarmDTO update(FarmDTO farmDTO);
    void deleteById(UUID id);
    FarmDTO getFarmDetails(UUID id);
    Page<FarmDTO> searchFarms(FarmSearchCriteria criteria, Pageable pageable);
    void validateFarmData(FarmDTO farmDTO);
}