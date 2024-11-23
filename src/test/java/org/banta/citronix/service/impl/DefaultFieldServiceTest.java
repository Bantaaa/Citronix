package org.banta.citronix.service.impl;

import org.banta.citronix.domain.Farm;
import org.banta.citronix.domain.Field;
import org.banta.citronix.dto.field.FieldDTO;
import org.banta.citronix.mapper.FieldMapper;
import org.banta.citronix.repository.FarmRepository;
import org.banta.citronix.repository.FieldRepository;
import org.banta.citronix.web.errors.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultFieldServiceTest {

    @Mock
    private FieldRepository fieldRepository;

    @Mock
    private FarmRepository farmRepository;

    @Mock
    private FieldMapper fieldMapper;

    @InjectMocks
    private DefaultFieldService fieldService;

    private UUID fieldId;
    private FieldDTO fieldDTO;

    @BeforeEach
    void setUp() {
        fieldId = UUID.randomUUID();
        fieldDTO = FieldDTO.builder()
                .id(fieldId)
                .area(2000.0)
                .farmId(UUID.randomUUID())
                .build();
    }

    @Test
    void saveField_Success() {
        // Arrange
        Farm farm = new Farm();
        farm.setArea(10000f);  // Set a valid area (1 hectare in square meters)

        when(farmRepository.findById(any())).thenReturn(Optional.of(farm));
        when(fieldRepository.sumAreaByFarmId(any())).thenReturn(0.0); // Add this for area validation
        when(fieldRepository.save(any())).thenReturn(new Field());
        when(fieldMapper.toDto(any())).thenReturn(fieldDTO);

        // Act
        FieldDTO result = fieldService.saveField(fieldDTO);

        // Assert
        assertNotNull(result);
        verify(fieldRepository).save(any());
    }

    @Test
    void getFieldById_Success() {
        // Arrange
        Field field = new Field();
        when(fieldRepository.findById(fieldId)).thenReturn(Optional.of(field));
        when(fieldMapper.toDto(field)).thenReturn(fieldDTO);

        // Act
        FieldDTO result = fieldService.getFieldById(fieldId);

        // Assert
        assertNotNull(result);
        assertEquals(fieldDTO.getId(), result.getId());
    }

    @Test
    void getFieldById_NotFound() {
        // Arrange
        when(fieldRepository.findById(fieldId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> fieldService.getFieldById(fieldId));
    }

    @Test
    void updateField_Success() {
        // Arrange
        Field field = new Field();
        Farm farm = new Farm();
        farm.setArea(10000f);  // 1 hectare
        field.setFarm(farm);
        field.setArea(2000.0); // 0.2 hectare

        when(fieldRepository.findById(fieldId)).thenReturn(Optional.of(field));
        when(fieldRepository.save(any())).thenReturn(field);
        when(fieldMapper.toDto(any())).thenReturn(fieldDTO);

        // Act
        FieldDTO result = fieldService.updateField(fieldDTO);

        // Assert
        assertNotNull(result);
        verify(fieldRepository).save(any());
    }
    @Test
    void deleteField_Success() {
        // Arrange
        when(fieldRepository.existsById(fieldId)).thenReturn(true);

        // Act
        fieldService.deleteField(fieldId);

        // Assert
        verify(fieldRepository).deleteById(fieldId);
    }
}