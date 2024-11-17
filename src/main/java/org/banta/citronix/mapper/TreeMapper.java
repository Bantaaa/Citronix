package org.banta.citronix.mapper;

import org.banta.citronix.domain.Tree;
import org.banta.citronix.dto.tree.TreeRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TreeMapper {
    TreeRequestDTO toDto(Tree tree);
    Tree toEntity(TreeRequestDTO treeDTO);
}