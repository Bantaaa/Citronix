package org.banta.citronix.service.impl;

import lombok.RequiredArgsConstructor;
import org.banta.citronix.domain.Harvest;
import org.banta.citronix.domain.HarvestDetail;
import org.banta.citronix.dto.harvest.HarvestDTO;
import org.banta.citronix.dto.harvest.HarvestDetailDTO;
import org.banta.citronix.mapper.HarvestMapper;
import org.banta.citronix.repository.HarvestRepository;
import org.banta.citronix.service.HarvestService;
import org.banta.citronix.web.errors.exception.BadRequestException;
import org.banta.citronix.web.errors.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DefaultHarvestService implements HarvestService {
    private final HarvestRepository harvestRepository;
    private final HarvestMapper harvestMapper;

    @Override
    public HarvestDTO createHarvest(HarvestDTO harvestDTO) {
        if (existsBySeasonAndYear(harvestDTO)) {
            throw new BadRequestException("A harvest for this season already exists");
        }

        if (isTreeHarvestedInSeason(harvestDTO)) {
            throw new BadRequestException("Some trees have already been harvested this season");
        }

        Harvest harvest = harvestMapper.toEntity(harvestDTO);

        // Calculate total quantity
        if (harvest.getHarvestDetails() != null) {
            double totalQuantity = harvest.getHarvestDetails().stream()
                    .mapToDouble(HarvestDetail::getQuantity)
                    .sum();
            harvest.setTotalQuantity(totalQuantity);
        }

        harvest = harvestRepository.save(harvest);
        return harvestMapper.toDto(harvest);
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
    @Transactional(readOnly = true)
    public HarvestDTO getHarvestById(UUID harvestId) {
        return harvestRepository.findById(harvestId)
                .map(harvestMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Harvest not found with id: " + harvestId));
    }

    @Override
    public Boolean isTreeHarvestedInSeason(HarvestDTO harvestDTO) {
        return harvestRepository.isTreeHarvestedInSeason(harvestDTO.getHarvestDetails().get(0).getTreeId(), harvestDTO.getSeason());
    }

    @Override
    public Boolean existsBySeasonAndYear(HarvestDTO harvestDTO) {
        return harvestRepository.existsBySeasonAndYear(harvestDTO.getSeason());
    }
}