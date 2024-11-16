package org.banta.citronix.service.impl;

import lombok.RequiredArgsConstructor;
import org.banta.citronix.domain.Farm;
import org.banta.citronix.dto.farm.FarmDTO;
import org.banta.citronix.mapper.FarmMapper;
import org.banta.citronix.repository.FarmRepository;
import org.banta.citronix.service.FarmService;
import org.banta.citronix.web.errors.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DefaultFarmService implements FarmService {
    private final FarmRepository farmRepository;
    private final FarmMapper farmMapper;

    @Override
    @Transactional
    public FarmDTO save(FarmDTO farmDTO) {
        Farm farm = farmMapper.toEntity(farmDTO);
        farm = farmRepository.save(farm);
        return farmMapper.toDto(farm);
    }

    @Override
    @Transactional
    public FarmDTO update(FarmDTO farmDTO) {
        Farm farm = farmMapper.toEntity(farmDTO);
        farm = farmRepository.save(farm);
        return farmMapper.toDto(farm);
    }

    @Override
    @Transactional
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


}