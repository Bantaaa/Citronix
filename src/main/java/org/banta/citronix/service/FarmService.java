package org.banta.citronix.service;

import org.banta.citronix.domain.Farm;
import org.banta.citronix.dto.FarmDTO;
import org.banta.citronix.mapper.FarmMapper;
import org.banta.citronix.repository.FarmRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FarmService {
    private FarmRepository farmRepository;
    private FarmMapper farmMapper;

    public FarmService(FarmRepository farmRepository, FarmMapper farmMapper) {
        this.farmRepository = farmRepository;
        this.farmMapper = farmMapper;
    }

    public FarmDTO save(FarmDTO farmDTO) {
        Farm farm = farmMapper.toEntity(farmDTO);
        farm = farmRepository.save(farm);
        return farmMapper.toDto(farm);
    }

    public FarmDTO update(FarmDTO farmDTO) {
        Farm farm = farmMapper.toEntity(farmDTO);
        farm = farmRepository.save(farm);
        return farmMapper.toDto(farm);
    }

    public void deleteById(UUID id) {
        farmRepository.deleteById(id);
    }

    public List<FarmDTO> findAll() {
        return farmRepository.findAll().stream()
                .map(farmMapper::toDto)
                .collect(Collectors.toList());
    }
}
