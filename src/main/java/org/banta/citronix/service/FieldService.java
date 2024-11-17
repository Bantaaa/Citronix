package org.banta.citronix.service;

import org.banta.citronix.domain.Field;
import org.banta.citronix.dto.field.CreateFieldRequest;
import org.banta.citronix.dto.field.FieldDTO;
import org.banta.citronix.dto.field.UpdateFieldRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public interface FieldService {
    FieldDTO saveField(CreateFieldRequest createFieldRequest);
    FieldDTO getFieldById(UUID id);
    List<FieldDTO> getAllFieldsByFarmId(UUID farmId);
    FieldDTO updateField(UUID id, UpdateFieldRequest updateFieldRequest);
    void deleteField(UUID id);
//    boolean isFieldAvailableForHarvest(Long fieldId, Season season);
//    boolean canAddTreeToField(Long fieldId);
}
