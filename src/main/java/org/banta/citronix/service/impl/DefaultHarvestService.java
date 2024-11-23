package org.banta.citronix.service.impl;

import lombok.RequiredArgsConstructor;
import org.banta.citronix.domain.Harvest;
import org.banta.citronix.domain.HarvestDetail;
import org.banta.citronix.domain.enums.Season;
import org.banta.citronix.dto.harvest.HarvestDTO;
import org.banta.citronix.dto.harvest.HarvestDetailDTO;
import org.banta.citronix.mapper.HarvestMapper;
import org.banta.citronix.repository.HarvestRepository;
import org.banta.citronix.service.HarvestService;
import org.banta.citronix.service.TreeService;
import org.banta.citronix.web.errors.exception.BadRequestException;
import org.banta.citronix.web.errors.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DefaultHarvestService implements HarvestService {
    private final HarvestRepository harvestRepository;
    private final HarvestMapper harvestMapper;
    private final TreeService treeService;

    @Override
    public HarvestDTO createHarvest(HarvestDTO harvestDTO) {;
        harvestDTO.setSeason(getSeasonByDate(harvestDTO.getHarvestDate()));
        validateHarvest(harvestDTO);
        Harvest harvest = harvestMapper.toEntity(harvestDTO);

        // Calculate total quantity
        if (harvest.getHarvestDetails() != null) {
            double totalQuantity = harvest.getHarvestDetails().stream()
                    .mapToDouble(HarvestDetail::getQuantity)
                    .sum();

            harvest.setTotalQuantity(totalQuantity);
        }
        if (harvest.getHarvestDetails() != null) {
            Harvest finalHarvest = harvest;
            harvest.getHarvestDetails().forEach(detail -> detail.setHarvest(finalHarvest));
        }

        harvest = harvestRepository.save(harvest);
        return harvestMapper.toDto(harvest);
    }

    private void validateHarvest(HarvestDTO harvestDTO) {
        if (existsBySeasonAndYear(harvestDTO, harvestDTO.getHarvestDate().getYear())) {
            throw new BadRequestException("A harvest for this season already exists");
        }

        if (harvestDTO.getHarvestDetails() == null || harvestDTO.getHarvestDetails().isEmpty()) {
            throw new BadRequestException("Harvest details are required");
        }

        if (harvestDTO.getHarvestDetails().stream().anyMatch(detail -> isTreeHarvestedInSeason(detail.getTreeId(), getSeasonByDate(harvestDTO.getHarvestDate()), harvestDTO.getHarvestDate().getYear()).equals("Tree has been harvested in this season"))) {
            throw new BadRequestException("Some trees have already been harvested this season");
        }

        singleFieldPerHarvest(harvestDTO);
    }

    @Override
    public void singleFieldPerHarvest(HarvestDTO harvestDTO) {
        List<HarvestDetailDTO> details = harvestDTO.getHarvestDetails();
        if (details.isEmpty()) return;

        int j = 1;
        UUID referenceFieldId = treeService.getTreeById(details.get(0).getTreeId()).getFieldId();

        while (j < details.size()) {
            UUID currentFieldId = treeService.getTreeById(details.get(j).getTreeId()).getFieldId();
            if (!referenceFieldId.equals(currentFieldId)) {
                throw new BadRequestException("Harvest should only be associated with one field");
            }
            j++;
        }
    }

    @Override
    public Season getSeasonByDate(LocalDate date) {
        int month = date.getMonthValue();
        if (month >= 3 && month <= 5) return Season.SPRING;
        if (month >= 6 && month <= 8) return Season.SUMMER;
        if (month >= 9 && month <= 11) return Season.AUTUMN;
        return Season.WINTER;
    }
    
    @Override
    public HarvestDTO updateHarvest(HarvestDTO harvestDTO) {
        if (!harvestRepository.existsById(harvestDTO.getId())) {
            throw new ResourceNotFoundException("Harvest not found with id: " + harvestDTO.getId());
        }
        Harvest harvest = harvestMapper.toEntity(harvestDTO);
        harvest = harvestRepository.save(harvest);
        return harvestMapper.toDto(harvest);
    }

    @Override
    public void deleteHarvest(UUID harvestId) {
        if (!harvestRepository.existsById(harvestId)) {
            throw new ResourceNotFoundException("Harvest not found with id: " + harvestId);
        }
        harvestRepository.deleteById(harvestId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HarvestDTO> getHarvests() {
        return harvestMapper.toDtoList(harvestRepository.findAll());
    }

    @Override
    public HarvestDTO getHarvestById(UUID harvestId) {
        return harvestRepository.findById(harvestId)
                .map(harvestMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Harvest not found with id: " + harvestId));
    }

    @Override
    public String isTreeHarvestedInSeason(UUID treeId, Season season, Integer year) {
        if (treeService.getTreeById(treeId).getId() == null) {
            throw new ResourceNotFoundException("Tree not found with id: " + treeId);
        }

        if (harvestRepository.isTreeHarvestedInSeason(treeId, season, year)) {
            return "Tree has been harvested in this season";
        }
        return "Tree has not been harvested in this season";
    }

    @Override
    public Boolean existsBySeasonAndYear(HarvestDTO harvestDTO, Integer year) {
        return harvestRepository.existsBySeasonAndYear(getSeasonByDate(harvestDTO.getHarvestDate()), year);
    }
}