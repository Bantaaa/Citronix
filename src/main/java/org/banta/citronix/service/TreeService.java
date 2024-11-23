package org.banta.citronix.service;

import jakarta.validation.Valid;
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

    @Validated(TreeDTO.Create.class)
    TreeDTO createTree(@Valid TreeDTO request);

    @Validated(TreeDTO.Update.class)
    TreeDTO updateTree(@Valid TreeDTO request);

    void deleteTree(UUID id);

    Double calculateProductivity(Long age);

    String determineStatus(Long age);

    boolean isValidPlantingDate(LocalDate plantingDate);
}