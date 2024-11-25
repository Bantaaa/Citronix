package org.banta.citronix.web.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.banta.citronix.dto.sale.SaleDTO;
import org.banta.citronix.service.SaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
public class SaleController {
    private final SaleService saleService;

    @PostMapping("/create")
    public ResponseEntity<SaleDTO> createSale(@RequestBody SaleDTO saleDTO) {
        return ResponseEntity.ok(saleService.createSale(saleDTO));
    }

    @PutMapping("/update")
    public ResponseEntity<SaleDTO> updateSale(@Valid @RequestBody SaleDTO saleDTO) {
        return ResponseEntity.ok(saleService.updateSale(saleDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable UUID id) {
        saleService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/details")
    public ResponseEntity<SaleDTO> getSaleDetails(@RequestBody SaleDTO saleDTO) {
        return ResponseEntity.ok(saleService.getSaleById(saleDTO.getId()));
    }

    @GetMapping("/all")
    public ResponseEntity<List<SaleDTO>> getAllSales() {
        return ResponseEntity.ok(saleService.getAllSales());
    }
}
