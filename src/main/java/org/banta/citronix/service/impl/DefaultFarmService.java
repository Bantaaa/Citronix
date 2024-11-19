package org.banta.citronix.service.impl;

import lombok.RequiredArgsConstructor;
import org.banta.citronix.domain.Farm;
import org.banta.citronix.domain.Field;
import org.banta.citronix.dto.farm.FarmDTO;
import org.banta.citronix.dto.farm.FarmSearchCriteria;
import org.banta.citronix.mapper.FarmMapper;
import org.banta.citronix.repository.FarmRepository;
import org.banta.citronix.service.FarmService;
import org.banta.citronix.specification.FarmSpecifications;
import org.banta.citronix.web.errors.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultFarmService implements FarmService {
    private final FarmRepository farmRepository;
    private final FarmMapper farmMapper;

    @Override
    public FarmDTO save(FarmDTO farmDTO) {
        if (farmDTO.getFields() == null) {
            throw new ResourceNotFoundException("Farm must have a list of fields");
        }
        Farm farm = farmMapper.toEntity(farmDTO);
        farm = farmRepository.save(farm);
        return farmMapper.toDto(farm);
    }

    @Override
    public FarmDTO update(FarmDTO farmDTO) {
        Farm farm = farmMapper.toEntity(farmDTO);
        farm = farmRepository.save(farm);
        return farmMapper.toDto(farm);
    }

    @Override
    public void deleteById(UUID id) {
        farmRepository.deleteById(id);
    }


    public FarmDTO getFarmDetails(UUID id) {
        Farm farm = farmRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Farm not found with id: %s", id)
                ));
        return farmMapper.toDto(farm);
    }

    public List<FarmDTO> searchFarms(FarmSearchCriteria criteria) {
        return farmRepository.findAll(FarmSpecifications.withCriteria(criteria))
                .stream()
                .map(farmMapper::toDto)
                .toList();
    }

    public List<FarmDTO> getFarmsWithSumFieldsAreaLessThan4000() {
        List<Farm> farms = farmRepository.findAll();

        farms.removeIf(farm -> farm.getFields().stream().mapToDouble(Field::getArea).sum() >= 4000);

        return farms.stream()
                .map(farmMapper::toDto)
                .toList();
    }


}