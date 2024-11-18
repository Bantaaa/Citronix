package org.banta.citronix.web.rest;

import lombok.RequiredArgsConstructor;
import org.banta.citronix.dto.sale.SaleDTO;
import org.banta.citronix.service.SaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
public class SaleController {
    private final SaleService saleService;

    @PostMapping("/create")
    public ResponseEntity<SaleDTO> createSale(@RequestBody SaleDTO saleDTO) {
        return ResponseEntity.ok(saleService.createSale(saleDTO));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteSale(@RequestBody SaleDTO saleDTO) {
        saleService.deleteSale(saleDTO.getId());
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
