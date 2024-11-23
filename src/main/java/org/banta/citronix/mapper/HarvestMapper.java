package org.banta.citronix.mapper;

import org.banta.citronix.domain.Harvest;
import org.banta.citronix.dto.harvest.HarvestDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {HarvestDetailMapper.class})
public interface HarvestMapper {

    @Mapping(target = "harvestDetails", source = "harvestDetails")
    HarvestDTO toDto(Harvest harvest);

    @Mapping(target = "harvestDetails", source = "harvestDetails")
    @Mapping(target = "totalQuantity", ignore = true)
    Harvest toEntity(HarvestDTO dto);

    List<HarvestDTO> toDtoList(List<Harvest> harvests);

    @AfterMapping
    default void linkHarvestDetails(@MappingTarget Harvest harvest) {
        if (harvest.getHarvestDetails() != null) {
            harvest.getHarvestDetails().forEach(detail -> detail.setHarvest(harvest));
        }
    }
}