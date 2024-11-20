package org.banta.citronix.mapper;

import org.banta.citronix.domain.Tree;
import org.banta.citronix.dto.tree.TreeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Mapper(componentModel = "spring")
public interface TreeMapper {
    @Mapping(target = "age", expression = "java(calculateAge(tree.getDatePlanted()))")
    @Mapping(target = "status", expression = "java(determineStatus(calculateAge(tree.getDatePlanted())))")
    @Mapping(target = "productivity", expression = "java(calculateProductivity(calculateAge(tree.getDatePlanted())))")
    @Mapping(target = "fieldId", source = "field.id")
    TreeDTO toDto(Tree tree);

    Tree toEntity(TreeDTO treeDTO);

    default Long calculateAge(LocalDate plantingDate) {
        return ChronoUnit.YEARS.between(plantingDate, LocalDate.now());
    }

    default String determineStatus(Long age) {
        if (age < 3) return "Young";
        if (age <= 10) return "Mature";
        return "Old";
    }

    default Double calculateProductivity(Long age) {
        if (age < 3) return 10.0;
        if (age <= 10) return 48.0;
        return 80.0;
    }
}