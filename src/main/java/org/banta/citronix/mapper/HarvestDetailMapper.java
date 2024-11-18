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
    @Mapping(source = "harvest.id", target = "harvestId")
    HarvestDetailDTO toDto(HarvestDetail detail);

    @Mapping(target = "tree", expression = "java(createTreeRef(dto.getTreeId()))")
    @Mapping(target = "harvest", ignore = true)
    HarvestDetail toEntity(HarvestDetailDTO dto);

    List<HarvestDetailDTO> toDtoList(List<HarvestDetail> details);

    default Tree createTreeRef(UUID id) {
        if (id == null) return null;
        Tree tree = new Tree();
        tree.setId(id);
        return tree;
    }
}