package org.banta.citronix.service.impl;

import lombok.RequiredArgsConstructor;
import org.banta.citronix.domain.Farm;
import org.banta.citronix.domain.Field;
import org.banta.citronix.dto.field.CreateFieldRequest;
import org.banta.citronix.dto.field.FieldDTO;
import org.banta.citronix.dto.field.UpdateFieldRequest;
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

@Service
@Transactional
@RequiredArgsConstructor
public class DefaultFieldService implements FieldService {
    private final FieldRepository fieldRepository;
    private final FarmRepository farmRepository;
    private final FieldMapper fieldMapper;

    private static final int MAX_FIELDS_PER_FARM = 10;

    public FieldDTO saveField(CreateFieldRequest request) {
        Farm farm = farmRepository.findById(request.getFarmId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Farm not found with id: %s", request.getFarmId())
                ));

        validateFieldCount(farm.getId());
        validateFieldArea(request.getArea(), farm);

        Field field = Field.builder()
                .area(request.getArea())
                .farm(farm)
                .trees(new ArrayList<>())
                .build();

        field = fieldRepository.save(field);
        return fieldMapper.toDto(field);
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
    public List<FieldDTO> getAllFields() {
        return fieldRepository.findAll().stream()
                .map(fieldMapper::toDto)
                .toList();
    }

    public FieldDTO updateField(UUID id, UpdateFieldRequest request) {
        Field field = fieldRepository.findById(UUID.fromString(id.toString()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Field not found with id: %s", id)
                ));

        if (request.getArea() != null && !request.getArea().equals(field.getArea())) {
            validateFieldArea(request.getArea(), field.getFarm());
            field.setArea(request.getArea());
        }

        field = fieldRepository.save(field);
        return fieldMapper.toDto(field);
    }

    public void deleteField(UUID id) {
        if (!fieldRepository.existsById(UUID.fromString(id.toString()))) {
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
// KAYN CHI BLAN HNA TA TRJ3LIH
    private void validateFieldArea(Float fieldArea, Farm farm) {
        // Check if field area exceeds 50% of farm area
        float maxAllowedFieldArea = farm.getArea() * 0.5f;

        if (fieldArea > maxAllowedFieldArea) {
            throw new BadRequestException(
                    String.format("Field area (%.2f m²) cannot exceed 50%% of farm area (%.2f m²)",
                            fieldArea,
                            maxAllowedFieldArea)
            );
        }
    }
}