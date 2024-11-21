package org.banta.citronix.mapper;

import org.banta.citronix.domain.Farm;
import org.banta.citronix.dto.farm.FarmDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {FieldMapper.class})
public interface FarmMapper {
    @Mapping(target = "fields")
    Farm toEntity(FarmDTO farmDTO);
    FarmDTO toDto(Farm farm);
}