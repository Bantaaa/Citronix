package org.banta.citronix.mapper;

import org.banta.citronix.domain.Harvest;
import org.banta.citronix.domain.HarvestDetail;
import org.banta.citronix.dto.harvest.HarvestDTO;
import org.banta.citronix.dto.harvest.HarvestDetailDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {HarvestDetailMapper.class})
public interface HarvestMapper {
    @Mapping(source = "harvestDetails", target = "harvestDetails")
    HarvestDTO toDto(Harvest harvest);

    @Mapping(source = "harvestDetails", target = "harvestDetails")
    Harvest toEntity(HarvestDTO harvestDTO);

    List<HarvestDTO> toDtoList(List<Harvest> harvests);
}