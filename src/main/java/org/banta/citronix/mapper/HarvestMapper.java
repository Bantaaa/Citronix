package org.banta.citronix.mapper;

import org.banta.citronix.domain.Harvest;
import org.banta.citronix.dto.harvest.HarvestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {HarvestDetailMapper.class})
public interface HarvestMapper {
    HarvestDTO toDto(Harvest harvest);

    Harvest toEntity(HarvestDTO harvestDTO);

    List<HarvestDTO> toDtoList(List<Harvest> harvests);
}
