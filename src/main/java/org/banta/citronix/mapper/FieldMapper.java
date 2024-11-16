package org.banta.citronix.mapper;

import org.banta.citronix.domain.Field;
import org.banta.citronix.dto.FieldDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TreeMapper.class})
public interface FieldMapper {
    FieldDTO toDto(Field field);
    Field toEntity(FieldDTO fieldDTO);
}