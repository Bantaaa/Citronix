package org.banta.citronix.service;

import org.banta.citronix.dto.tree.TreeDTO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Validated
public interface TreeService {
    List<TreeDTO> getAllTrees();
    TreeDTO getTreeById(UUID id);
    TreeDTO createTree(TreeDTO treeRequest);
    TreeDTO updateTree(TreeDTO treeRequest);
    void deleteTree(UUID id);

    // Business logic methods
    Double calculateProductivity(Long age);
    String determineStatus(Long age);
    boolean isValidPlantingDate(LocalDate plantingDate);
//    boolean canAddTreeToField(UUID fieldId); // Check density constraint
}