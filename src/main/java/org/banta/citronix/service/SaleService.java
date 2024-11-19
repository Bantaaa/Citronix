package org.banta.citronix.service;

import org.banta.citronix.dto.sale.SaleDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface SaleService {
    SaleDTO createSale(SaleDTO saleDTO);
    void deleteSale(UUID id);
    SaleDTO getSaleById(UUID id);
    List<SaleDTO> getAllSales();
}