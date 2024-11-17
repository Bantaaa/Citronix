package org.banta.citronix.service;

import org.banta.citronix.dto.tree.TreeRequestDTO;
import org.banta.citronix.dto.tree.TreeResponseDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public interface TreeService {
    List<TreeResponseDTO> getAllTrees();
    TreeResponseDTO getTreeById(UUID id);
    TreeResponseDTO createTree(TreeRequestDTO treeRequest);
    TreeResponseDTO updateTree(UUID id, TreeRequestDTO treeRequest);
    void deleteTree(UUID id);

    // Business logic methods
    Double calculateProductivity(Long age);
    String determineStatus(Long age);
    boolean isValidPlantingDate(LocalDate plantingDate);
//    boolean canAddTreeToField(UUID fieldId); // Check density constraint
}