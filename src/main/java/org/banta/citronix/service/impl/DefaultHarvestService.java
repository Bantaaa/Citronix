package org.banta.citronix.service.impl;

import lombok.RequiredArgsConstructor;
import org.banta.citronix.domain.Harvest;
import org.banta.citronix.domain.HarvestDetail;
import org.banta.citronix.domain.enums.Season;
import org.banta.citronix.dto.harvest.HarvestDTO;
import org.banta.citronix.dto.harvest.HarvestDetailDTO;
import org.banta.citronix.dto.tree.TreeDTO;
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
    public HarvestDTO createHarvest(HarvestDTO harvestDTO) {
        harvestDTO.setSeason(getSeasonByDate(harvestDTO.getHarvestDate()));
        validateHarvest(harvestDTO);

        Harvest harvest = harvestMapper.toEntity(harvestDTO);

        if (harvest.getHarvestDetails() != null) {
            double totalQuantity = harvest.getHarvestDetails().stream()
                    .mapToDouble(HarvestDetail::getQuantity)
                    .sum();
            harvest.setTotalQuantity(totalQuantity);

            Harvest finalHarvest = harvest;
            harvest.getHarvestDetails().forEach(detail -> detail.setHarvest(finalHarvest));
        }

        harvest = harvestRepository.save(harvest);
        return harvestMapper.toDto(harvest);
    }

    private void validateHarvest(HarvestDTO harvestDTO) {
        if (harvestDTO.getHarvestDetails() == null || harvestDTO.getHarvestDetails().isEmpty()) {
            throw new BadRequestException("Harvest details are required");
        }

        List<HarvestDetailDTO> details = harvestDTO.getHarvestDetails();

        details.forEach(detail -> {
            if (detail.getQuantity() == null || detail.getQuantity() <= 0) {
                TreeDTO tree = treeService.getTreeById(detail.getTreeId());
                detail.setQuantity(tree.getProductivity()/ 4);
            }
        });

        Season season = getSeasonByDate(harvestDTO.getHarvestDate());
        int year = harvestDTO.getHarvestDate().getYear();

        // For each tree, check if its field already has a harvest this season
        for (HarvestDetailDTO detail : harvestDTO.getHarvestDetails()) {
            UUID fieldId = treeService.getTreeById(detail.getTreeId()).getFieldId();
            if (harvestRepository.existsByFieldAndSeasonAndYear(fieldId, season, year)) {
                throw new BadRequestException("One or more fields already have a harvest for this season");
            }
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
}