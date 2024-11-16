package org.banta.citronix.web.rest;

import org.banta.citronix.domain.Farm;
import org.banta.citronix.dto.FarmDTO;
import org.banta.citronix.service.FarmService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/farms")
public class FarmController {

    private final FarmService farmService;

    public FarmController(FarmService farmService) {
        this.farmService = farmService;
    }

    @PostMapping
    public ResponseEntity<FarmDTO> save(@RequestBody FarmDTO farmDTO) {
        return ResponseEntity.ok(farmService.save(farmDTO));
    }

    @PutMapping
    public ResponseEntity<FarmDTO> update(@RequestBody FarmDTO farmDTO) {
        return ResponseEntity.ok(farmService.update(farmDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        farmService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<FarmDTO>> getAll() {
        return ResponseEntity.ok(farmService.findAll());
    }

}
