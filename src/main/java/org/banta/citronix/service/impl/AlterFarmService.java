package org.banta.citronix.service.impl;

import lombok.RequiredArgsConstructor;
import org.banta.citronix.domain.Farm;
import org.banta.citronix.dto.farm.FarmDTO;
import org.banta.citronix.dto.farm.FarmSearchCriteria;
import org.banta.citronix.mapper.FarmMapper;
import org.banta.citronix.repository.FarmRepository;
import org.banta.citronix.service.FarmService;
import org.banta.citronix.web.errors.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlterFarmService implements FarmService {
    private final FarmRepository farmRepository;
    private final FarmMapper farmMapper;

    @Override
    public FarmDTO save(FarmDTO farmDTO) {
        if (farmDTO.getFields() != null) {
            throw new ResourceNotFoundException("Farm doesn't have any fields");
        }
        Farm farm = farmMapper.toEntity(farmDTO);
        farm = farmRepository.save(farm);
        return farmMapper.toDto(farm);
    }

    public FarmDTO update(FarmDTO farmDTO) {
        return null;
    }

    public void deleteById(UUID id) {

    }

    public FarmDTO getFarmDetails(UUID id) {
        return null;
    }

    public List<FarmDTO> searchFarms(FarmSearchCriteria criteria) {
        return List.of();
    }

    @Override
    public List<FarmDTO> getFarmsWithSumFieldsAreaLessThan4000() {
        return List.of();
    }
}
