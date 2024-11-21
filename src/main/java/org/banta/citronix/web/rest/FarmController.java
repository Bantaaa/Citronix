package org.banta.citronix.web.rest;

import org.banta.citronix.dto.farm.FarmDTO;
import org.banta.citronix.dto.farm.FarmSearchCriteria;
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

    @PostMapping("/create")
    public ResponseEntity<FarmDTO> createFarm(@RequestBody FarmDTO farmDTO) {

        return ResponseEntity.ok(farmService.save(farmDTO));
    }

    @PutMapping("/update")
    public ResponseEntity<FarmDTO> updateFarm(@RequestBody FarmDTO farmDTO) {
        return ResponseEntity.ok(farmService.update(farmDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        farmService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<FarmDTO> getFarmDetails(@PathVariable UUID id) {
        return ResponseEntity.ok(farmService.getFarmDetails(id));
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchFarms(@RequestBody FarmSearchCriteria criteria) {
        if (isEmpty(criteria)) {
            return ResponseEntity
                    .badRequest()
                    .body("At least one search criterion must be provided");
        }
        return ResponseEntity.ok(farmService.searchFarms(criteria));
    }

    private boolean isEmpty(FarmSearchCriteria criteria) {
        return (criteria.getName() == null || criteria.getName().trim().isEmpty()) &&
                (criteria.getLocation() == null || criteria.getLocation().trim().isEmpty()) &&
                criteria.getMinArea() == null;
    }

}
