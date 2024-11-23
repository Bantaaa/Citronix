package org.banta.citronix.web.rest;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.banta.citronix.dto.harvest.HarvestDTO;
import org.banta.citronix.dto.harvest.HarvestDetailDTO;
import org.banta.citronix.domain.enums.Season;
import org.banta.citronix.service.HarvestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/harvests")
@RequiredArgsConstructor
public class HarvestController {
    private final HarvestService harvestService;

    @PostMapping("/create")
    public ResponseEntity<HarvestDTO> createHarvest(@Valid @RequestBody HarvestDTO harvestDTO) {
        return ResponseEntity.ok(harvestService.createHarvest(harvestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HarvestDTO> getHarvestById(@PathVariable UUID id) {
        return ResponseEntity.ok(harvestService.getHarvestById(id));
    }

    @GetMapping
    public ResponseEntity<List<HarvestDTO>> getAllHarvests() {
        return ResponseEntity.ok(harvestService.getHarvests());
    }

    @PutMapping("/update")
    public ResponseEntity<HarvestDTO> updateHarvest(@Valid @RequestBody HarvestDTO harvestDTO) {
        return ResponseEntity.ok(harvestService.updateHarvest(harvestDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteHarvest(@PathVariable UUID id) {
        harvestService.deleteHarvest(id);
        return ResponseEntity.noContent().build();
    }
}