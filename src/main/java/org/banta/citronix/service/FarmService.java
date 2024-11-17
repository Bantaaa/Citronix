package org.banta.citronix.service;

import org.banta.citronix.dto.farm.FarmDTO;
import org.banta.citronix.dto.farm.FarmSearchCriteria;

import java.util.List;
import java.util.UUID;

public interface FarmService {
    FarmDTO save(FarmDTO farmDTO);
    FarmDTO update(FarmDTO farmDTO);
    void deleteById(UUID id);
    FarmDTO getFarmDetails(UUID id);
    List<FarmDTO> searchFarms(FarmSearchCriteria criteria);
}