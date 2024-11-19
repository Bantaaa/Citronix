package org.banta.citronix.mapper;

import org.banta.citronix.domain.Harvest;
import org.banta.citronix.dto.harvest.HarvestDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {HarvestDetailMapper.class})
public interface HarvestMapper {
    HarvestDTO toDto(Harvest harvest);

    @Mapping(target = "totalQuantity", ignore = true)
    Harvest toEntity(HarvestDTO harvestDTO);
}