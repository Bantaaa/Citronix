package org.banta.citronix.service.impl;

import lombok.RequiredArgsConstructor;
import org.banta.citronix.domain.Farm;
import org.banta.citronix.dto.farm.FarmDTO;
import org.banta.citronix.dto.farm.FarmSearchCriteria;
import org.banta.citronix.mapper.FarmMapper;
import org.banta.citronix.repository.FarmRepository;
import org.banta.citronix.service.FarmService;
import org.banta.citronix.specification.FarmSpecifications;
import org.banta.citronix.web.errors.exception.BadRequestException;
import org.banta.citronix.web.errors.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DefaultFarmService implements FarmService {
    private final FarmRepository farmRepository;
    private final FarmMapper farmMapper;

    @Override
    public FarmDTO save(FarmDTO farmDTO) {
        validateFarmData(farmDTO);
        Farm farm = farmMapper.toEntity(farmDTO);
        farm = farmRepository.save(farm);
        return farmMapper.toDto(farm);
    }

    @Override
    public FarmDTO update(FarmDTO farmDTO) {
        if (farmDTO.getId() == null) {
            throw new BadRequestException("Farm ID is required for update");
        }

        Farm existingFarm = farmRepository.findById(farmDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Farm not found with id: %s", farmDTO.getId())
                ));

        // validation
        if (farmDTO.getArea() != null) {
            validateArea(farmDTO.getArea());
        }
        if (farmDTO.getName() != null) {
            validateName(farmDTO.getName());
        }
        if (farmDTO.getLocation() != null) {
            validateLocation(farmDTO.getLocation());
        }
        if (farmDTO.getDateEstablished() != null) {
            validateEstablishmentDate(farmDTO.getDateEstablished());
        }

        // Update only non-null fields
        if (farmDTO.getArea() != null) {
            existingFarm.setArea(farmDTO.getArea());
        }
        if (farmDTO.getName() != null) {
            existingFarm.setName(farmDTO.getName());
        }
        if (farmDTO.getLocation() != null) {
            existingFarm.setLocation(farmDTO.getLocation());
        }
        if (farmDTO.getDateEstablished() != null) {
            existingFarm.setDateEstablished(farmDTO.getDateEstablished());
        }

        existingFarm = farmRepository.save(existingFarm);
        return farmMapper.toDto(existingFarm);
    }

    @Override
    public void deleteById(UUID id) {
        farmRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Farm not found with id: %s", id)));
        farmRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public FarmDTO getFarmDetails(UUID id) {
        Farm farm = farmRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Farm not found with id: %s", id)));


        return farmMapper.toDto(farm);
    }

    @Override
    public void validateFarmData(FarmDTO farmDTO) {
        validateArea(farmDTO.getArea());
        validateName(farmDTO.getName());
        validateLocation(farmDTO.getLocation());
        validateEstablishmentDate(farmDTO.getDateEstablished());
    }

    private void validateArea(Float area) {
        if (area == null || area < 2000) {
            throw new BadRequestException("Farm area must be at least 0.2 hectare (2000 m²)");
        }
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new BadRequestException("Farm name is required");
        }
    }

    private void validateLocation(String location) {
        if (location == null || location.trim().isEmpty()) {
            throw new BadRequestException("Farm location is required");
        }
    }

    private void validateEstablishmentDate(LocalDate date) {
        if (date == null) {
            throw new BadRequestException("Establishment date is required");
        }
        if (date.isAfter(LocalDate.now())) {
            throw new BadRequestException("Establishment date cannot be in the future");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<FarmDTO> searchFarms(FarmSearchCriteria criteria) {
        return farmRepository.findAll(FarmSpecifications.withCriteria(criteria))
                .stream()
                .map(farmMapper::toDto)
                .toList();
    }
}