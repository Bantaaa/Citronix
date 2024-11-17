package org.banta.citronix.service.impl;

import lombok.RequiredArgsConstructor;
import org.banta.citronix.domain.Field;
import org.banta.citronix.domain.Tree;
import org.banta.citronix.dto.tree.TreeRequestDTO;
import org.banta.citronix.dto.tree.TreeResponseDTO;
import org.banta.citronix.repository.FieldRepository;
import org.banta.citronix.repository.TreeRepository;
import org.banta.citronix.web.errors.exception.BadRequestException;
import org.banta.citronix.web.errors.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.banta.citronix.service.TreeService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultTreeService implements TreeService {
    private final TreeRepository treeRepository;
    private final FieldRepository fieldRepository;


    public void deleteTree(UUID id) {
        Tree tree = treeRepository.findById(UUID.fromString(id.toString()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Tree not found with id: %s", id)
                ));
        treeRepository.delete(tree);
    }

//    public boolean canAddTreeToField(UUID fieldId) {
//        return fieldRepository.countByFieldId(fieldId) < 100;
//    }

    public boolean isValidPlantingDate(LocalDate plantingDate) {
        return !plantingDate.isBefore(LocalDate.now());
    }

    public String determineStatus(Long age) {
        if (age < 5) {
            return "Seedling";
        } else if (age < 10) {
            return "Young";
        } else if (age < 20) {
            return "Mature";
        } else {
            return "Old";
        }
    }

    public Double calculateProductivity(Long age) {
        if (age < 5) {
            return 0.0;
        } else if (age < 10) {
            return 5.0;
        } else if (age < 20) {
            return 10.0;
        } else {
            return 15.0;
        }
    }

    public TreeResponseDTO createTree(TreeRequestDTO request) {
        Field field = fieldRepository.findById(request.getFieldId())
                .orElseThrow(() -> new ResourceNotFoundException("Field not found with id: " + request.getFieldId()));

//        if (!canAddTreeToField(field.getId()) || isValidPlantingDate(request.getDatePlanted()))
//            throw new BadRequestException("Invalid tree creation conditions");

        Tree tree = treeRepository.save(new Tree(null, request.getDatePlanted(), field));
        Long age = ChronoUnit.YEARS.between(tree.getDatePlanted(), LocalDate.now());

        return TreeResponseDTO.builder()
                .id(tree.getId())
                .datePlanted(tree.getDatePlanted())
                .age(age)
                .status(determineStatus(age))
                .productivity(calculateProductivity(age))
                .fieldId(field.getId())
                .seasonalProductivity(calculateProductivity(age))
                .isProductionPeriod(age < 20)
                .build();
    }

    public TreeResponseDTO getTreeById(UUID id) {
        Tree tree = treeRepository.findById(UUID.fromString(id.toString()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Tree not found with id: %s", id)
                ));
        Long age = ChronoUnit.YEARS.between(tree.getDatePlanted(), LocalDate.now());

        return TreeResponseDTO.builder()
                .id(tree.getId())
                .datePlanted(tree.getDatePlanted())
                .age(age)
                .status(determineStatus(age))
                .productivity(calculateProductivity(age))
                .fieldId(tree.getField().getId())
                .seasonalProductivity(calculateProductivity(age))
                .isProductionPeriod(age < 20)
                .build();
    }

    public TreeResponseDTO updateTree(UUID id, TreeRequestDTO request) {
        Tree tree = treeRepository.findById(UUID.fromString(id.toString()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Tree not found with id: %s", id)
                ));

        if (isValidPlantingDate(request.getDatePlanted()))
            throw new BadRequestException("Invalid tree update conditions");

        tree.setDatePlanted(request.getDatePlanted());
        tree = treeRepository.save(tree);
        Long age = ChronoUnit.YEARS.between(tree.getDatePlanted(), LocalDate.now());

        return TreeResponseDTO.builder()
                .id(tree.getId())
                .datePlanted(tree.getDatePlanted())
                .age(age)
                .status(determineStatus(age))
                .productivity(calculateProductivity(age))
                .fieldId(tree.getField().getId())
                .seasonalProductivity(calculateProductivity(age))
                .isProductionPeriod(age < 20)
                .build();
    }

    public List<TreeResponseDTO> getAllTrees() {
        return treeRepository.findAll().stream()
                .map(tree -> {
                    Long age = ChronoUnit.YEARS.between(tree.getDatePlanted(), LocalDate.now());
                    return TreeResponseDTO.builder()
                            .id(tree.getId())
                            .datePlanted(tree.getDatePlanted())
                            .age(age)
                            .status(determineStatus(age))
                            .productivity(calculateProductivity(age))
                            .fieldId(tree.getField().getId())
                            .seasonalProductivity(calculateProductivity(age))
                            .isProductionPeriod(age < 20)
                            .build();
                })
                .collect(Collectors.toList());
    }

//    public boolean isProductionPeriod(Long age) {
//        return age < 20;
//    }

}
