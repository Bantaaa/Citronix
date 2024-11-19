package org.banta.citronix.service.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.banta.citronix.domain.Field;
import org.banta.citronix.domain.Tree;
import org.banta.citronix.dto.tree.TreeDTO;
import org.banta.citronix.repository.FieldRepository;
import org.banta.citronix.repository.TreeRepository;
import org.banta.citronix.web.errors.exception.BadRequestException;
import org.banta.citronix.web.errors.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.banta.citronix.service.TreeService;
import org.springframework.validation.annotation.Validated;

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
        Tree tree = treeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tree not found with id: " + id));
        treeRepository.delete(tree);
    }

    public boolean isValidPlantingDate(LocalDate plantingDate) {
        return !plantingDate.isBefore(LocalDate.now());
    }

    public String determineStatus(Long age) {
        if (age < 3) return "YOUNG";
        if (age < 10) return "MATURE";
        if (age < 20) return "OLD";
        return "NON_PRODUCTIVE";
    }

    public Double calculateProductivity(Long age) {
        if (age < 3) return 2.5;
        if (age < 10) return 12.0;
        if (age < 20) return 20.0;
        return 0.0;
    }

    @Validated(TreeDTO.Create.class)
    public TreeDTO createTree(@Valid TreeDTO request) {
        Field field = fieldRepository.findById(request.getFieldId())
                .orElseThrow(() -> new ResourceNotFoundException("Field not found with id: " + request.getFieldId()));

        Tree tree = Tree.builder()
                .datePlanted(request.getDatePlanted())
                .field(field)
                .build();

        tree = treeRepository.save(tree);
        return buildTreeDTO(tree);
    }

    @Validated(TreeDTO.Update.class)
    public TreeDTO updateTree(@Valid TreeDTO request) {
        Tree tree = treeRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Tree not found with id: " + request.getId()));

        if (request.getDatePlanted() != null) {
            if (isValidPlantingDate(request.getDatePlanted())) {
                throw new BadRequestException("Invalid planting date");
            }
            tree.setDatePlanted(request.getDatePlanted());
        }

        tree = treeRepository.save(tree);
        return buildTreeDTO(tree);
    }

    private TreeDTO buildTreeDTO(Tree tree) {
        Long age = ChronoUnit.YEARS.between(tree.getDatePlanted(), LocalDate.now());
        return TreeDTO.builder()
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

    public TreeDTO getTreeById(UUID id) {
        return buildTreeDTO(treeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tree not found with id: " + id)));
    }

    public List<TreeDTO> getAllTrees() {
        return treeRepository.findAll().stream()
                .map(this::buildTreeDTO)
                .collect(Collectors.toList());
    }
}
