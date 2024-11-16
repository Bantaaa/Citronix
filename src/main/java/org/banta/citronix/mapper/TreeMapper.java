package org.banta.citronix.mapper;

import org.banta.citronix.domain.Tree;
import org.banta.citronix.dto.TreeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TreeMapper {
    TreeDTO toDto(Tree tree);
    Tree toEntity(TreeDTO treeDTO);
}