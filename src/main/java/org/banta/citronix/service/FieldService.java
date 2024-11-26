package org.banta.citronix.service;

import org.banta.citronix.domain.Field;
import org.banta.citronix.dto.farm.FarmDTO;
import org.banta.citronix.dto.field.FieldDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public interface FieldService {
    FieldDTO saveField(FieldDTO request);
    FieldDTO getFieldById(UUID id);
    List<FieldDTO> getAllFieldsByFarmId(UUID farmId);
    FieldDTO updateField(FieldDTO request);

    FarmDTO getFarmByFieldId(UUID fieldId);

    void deleteField(UUID id);
//    boolean isFieldAvailableForHarvest(Long fieldId, Season season);
//    boolean canAddTreeToField(Long fieldId);
}
