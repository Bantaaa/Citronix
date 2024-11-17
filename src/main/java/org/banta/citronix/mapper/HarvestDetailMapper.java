package org.banta.citronix.mapper;

import org.banta.citronix.domain.HarvestDetail;
import org.banta.citronix.dto.harvest.HarvestDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HarvestDetailMapper {
    @Mapping(source = "tree.id", target = "treeId")
    @Mapping(source = "harvest.id", target = "harvestId")
    HarvestDetailDTO toDto(HarvestDetail harvestDetail);

    @Mapping(source = "treeId", target = "tree.id")
    @Mapping(source = "harvestId", target = "harvest.id")
    HarvestDetail toEntity(HarvestDetailDTO dto);

    List<HarvestDetailDTO> toDtoList(List<HarvestDetail> details);
}