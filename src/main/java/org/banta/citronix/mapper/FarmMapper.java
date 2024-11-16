package org.banta.citronix.mapper;

import org.banta.citronix.domain.Farm;
import org.banta.citronix.dto.FarmDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {FieldMapper.class})
public interface FarmMapper {
    FarmDTO toDto(Farm farm);
    Farm toEntity(FarmDTO farmDTO);
}