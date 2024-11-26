package org.banta.citronix.service.impl;

import lombok.RequiredArgsConstructor;
import org.banta.citronix.domain.Farm;
import org.banta.citronix.domain.Field;
import org.banta.citronix.dto.farm.FarmDTO;
import org.banta.citronix.dto.field.FieldDTO;
import org.banta.citronix.mapper.FarmMapper;
import org.banta.citronix.service.FarmService;
import org.banta.citronix.web.errors.exception.BadRequestException;
import org.banta.citronix.web.errors.exception.ResourceNotFoundException;
import org.banta.citronix.mapper.FieldMapper;
import org.banta.citronix.repository.FarmRepository;
import org.banta.citronix.repository.FieldRepository;
import org.banta.citronix.service.FieldService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DefaultFieldService implements FieldService {
    private final FieldRepository fieldRepository;
    private final FarmRepository farmRepository;
    private final FieldMapper fieldMapper;
    private final FarmService farmService;
    private final FarmMapper farmMapper;

    private static final int MAX_FIELDS_PER_FARM = 10;

    public FieldDTO saveField(FieldDTO request) {
        Farm farm = farmRepository.findById(request.getFarmId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Farm not found with id: %s", request.getFarmId())
                ));

        validateFieldCount(farm.getId());
        validateFieldArea(request.getArea(), farm);

        return fieldMapper.toDto(fieldRepository.save(Field.builder()
                .area(request.getArea())
                .farm(farm)
                .trees(new ArrayList<>())
                .build()));
    }

    @Transactional(readOnly = true)
    public FieldDTO getFieldById(UUID id) {
        Field field = fieldRepository.findById(UUID.fromString(id.toString()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Field not found with id: %s", id)
                ));
        return fieldMapper.toDto(field);
    }

    @Transactional(readOnly = true)
    public List<FieldDTO> getAllFieldsByFarmId(UUID farmId) {
        List<Field> fields = fieldRepository.findByFarmId(farmId);
        return fields.stream()
                .map(fieldMapper::toDto)
                .collect(Collectors.toList());
    }

    public FieldDTO updateField(FieldDTO request) {
        FarmDTO farm = getFarmByFieldId(request.getId());
        validateFieldCount(farm.getId());
        validateFieldArea(request.getArea(), farmMapper.toEntity(farm));

        return fieldMapper.toDto(fieldRepository.save(Field.builder()
                .area(request.getArea())
                .farm(farmMapper.toEntity(farm))
                .build()));
    }

    @Transactional(readOnly = true)
    @Override
    public FarmDTO getFarmByFieldId(UUID fieldId) {
        Farm farm = fieldRepository.findFarmByFieldId(fieldId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Farm not found for field id: %s", fieldId)
                ));
        return farmMapper.toDto(farm);
    }

    public void deleteField(UUID id) {
        if (!fieldRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    String.format("Field not found with id: %s", id)
            );
        }
        fieldRepository.deleteById(UUID.fromString(id.toString()));
    }

    private void validateFieldCount(UUID farmId) {
        long fieldCount = fieldRepository.countByFarmId(farmId);
        if (fieldCount >= MAX_FIELDS_PER_FARM) {
            throw new BadRequestException(
                    String.format("Farm cannot have more than %d fields", MAX_FIELDS_PER_FARM)
            );
        }
    }

    private void validateFieldArea(Double fieldArea, Farm farm) {
        if (fieldArea < 1000) {
            throw new BadRequestException(
                    String.format("Field area must be at least 0.1 hectare (1000 m²), got %.2f m²", fieldArea)
            );
        }

        double maxAllowedFieldArea = farm.getArea() / 2;
        if (fieldArea > maxAllowedFieldArea) {
            throw new BadRequestException(
                    String.format("Field area (%.2f m²) cannot exceed 50%% of farm area (%.2f m²)",
                            fieldArea, maxAllowedFieldArea)
            );
        }

        Double currentTotalArea = fieldRepository.sumAreaByFarmId(farm.getId());
        if (currentTotalArea != null && (currentTotalArea + fieldArea) > farm.getArea()) {
            throw new BadRequestException(
                    String.format("Total field area (%.2f m²) would exceed farm area (%.2f m²)",
                            currentTotalArea + fieldArea, farm.getArea())
            );
        }
    }
}