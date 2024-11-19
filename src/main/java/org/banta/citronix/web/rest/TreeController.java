package org.banta.citronix.web.rest;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.banta.citronix.dto.tree.TreeDTO;
import org.banta.citronix.service.TreeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/trees")
@RequiredArgsConstructor
public class TreeController {
    private final TreeService treeService;

    @PostMapping("/create")
    public ResponseEntity<TreeDTO> createTree(@Valid @RequestBody TreeDTO request) {
        return new ResponseEntity<>(treeService.createTree(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreeDTO> getTreeById(@PathVariable UUID id) {
        return ResponseEntity.ok(treeService.getTreeById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TreeDTO>> getAllTrees() {
        return ResponseEntity.ok(treeService.getAllTrees());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TreeDTO> updateTree(@Valid @RequestBody TreeDTO request) {
        return ResponseEntity.ok(treeService.updateTree(request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTree(@PathVariable UUID id) {
        treeService.deleteTree(id);
        return ResponseEntity.noContent().build();
    }
}