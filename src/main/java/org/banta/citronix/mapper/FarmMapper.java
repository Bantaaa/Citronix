package org.banta.citronix.mapper;

import org.banta.citronix.domain.Farm;
import org.banta.citronix.dto.farm.FarmDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {FieldMapper.class})
public interface FarmMapper {
    FarmDTO toDto(Farm farm);
    Farm toEntity(FarmDTO farmDTO);
}