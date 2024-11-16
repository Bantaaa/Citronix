package org.banta.citronix.mapper;

import org.banta.citronix.domain.Field;
import org.banta.citronix.dto.field.FieldDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TreeMapper.class})
public interface FieldMapper {
    @Mapping(target = "farmId", source = "farm.id")
    FieldDTO toDto(Field field);

    @Mapping(target = "farm.id", source = "farmId")
    Field toEntity(FieldDTO fieldDTO);
}