package org.banta.citronix.service;

import org.banta.citronix.domain.enums.Season;
import org.banta.citronix.dto.harvest.HarvestDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public interface HarvestService {
    HarvestDTO createHarvest(HarvestDTO harvestDTO);
    HarvestDTO updateHarvest(HarvestDTO harvestDTO);
    void deleteHarvest(UUID harvestId);
    List<HarvestDTO> getHarvests();
    HarvestDTO getHarvestById(UUID harvestId);
    Season getSeasonByDate(LocalDate date);
    Boolean isTreeHarvestedInSeason(HarvestDTO harvestDTO);
    Boolean existsBySeasonAndYear(HarvestDTO harvestDTO, Integer year);
}
