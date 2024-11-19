package org.banta.citronix.mapper;

import org.banta.citronix.domain.HarvestDetail;
import org.banta.citronix.domain.Tree;
import org.banta.citronix.dto.harvest.HarvestDetailDTO;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface HarvestDetailMapper {
    @Mapping(source = "tree.id", target = "treeId")
    HarvestDetailDTO toDto(HarvestDetail detail);

    @Mapping(source = "treeId", target = "tree.id")
    HarvestDetail toEntity(HarvestDetailDTO dto);
}