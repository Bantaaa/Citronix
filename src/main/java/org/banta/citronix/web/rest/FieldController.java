package org.banta.citronix.web.rest;

import lombok.RequiredArgsConstructor;
import org.banta.citronix.dto.field.FieldDTO;
import org.banta.citronix.service.FieldService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/fields")
@RequiredArgsConstructor
public class FieldController {

    private final FieldService fieldService;

    @PostMapping("/save")
    public ResponseEntity<FieldDTO> createField(@Valid @RequestBody FieldDTO request) {
        return ResponseEntity.ok(fieldService.saveField(request));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<FieldDTO> getField(@PathVariable UUID id) {
        return ResponseEntity.ok(fieldService.getFieldById(id));
    }

    @GetMapping("/farm/{farmId}")
    public ResponseEntity<List<FieldDTO>> getFieldsByFarmId(@PathVariable UUID farmId) {
        return ResponseEntity.ok(fieldService.getAllFieldsByFarmId(farmId));
    }

    @PutMapping("/update")
    public ResponseEntity<FieldDTO> updateField(@Valid @RequestBody FieldDTO request) {
        return ResponseEntity.ok(fieldService.updateField(request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteField(@PathVariable UUID id) {
        fieldService.deleteField(id);
        return ResponseEntity.noContent().build();
    }
}