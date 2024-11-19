package org.banta.citronix.service.impl;

import lombok.RequiredArgsConstructor;
import org.banta.citronix.domain.Harvest;
import org.banta.citronix.domain.Sale;
import org.banta.citronix.dto.harvest.HarvestDTO;
import org.banta.citronix.dto.sale.SaleDTO;
import org.banta.citronix.mapper.HarvestMapper;
import org.banta.citronix.mapper.SaleMapper;
import org.banta.citronix.repository.SaleRepository;
import org.banta.citronix.service.HarvestService;
import org.banta.citronix.service.SaleService;
import org.banta.citronix.web.errors.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultSaleService implements SaleService {
    private final SaleRepository saleRepository;
    private final HarvestService harvestService;
    private final SaleMapper saleMapper;
    private final HarvestMapper harvestMapper;

    @Override
    public SaleDTO createSale(SaleDTO saleDTO) {
//        Harvest harvest = saleDTO.getHarvestId();
        HarvestDTO harvestdto = harvestService.getHarvestById(saleDTO.getHarvestId());
        if (harvestdto == null) {
            throw new ResourceNotFoundException(
                    String.format("Harvest not found with id: %s", saleDTO.getHarvestId())
            );
        }
        Double revenue = saleDTO.getUnitPrice() * harvestdto.getTotalQuantity();

        saleDTO.setRevenue(revenue);
//        Harvest harvest = harvestMapper.toEntity(harvestdto);
        Sale sale = saleMapper.toEntity(saleDTO);
        sale = saleRepository.save(sale);
        return saleDTO;
    }

    @Override
    public void deleteSale(UUID id) {
        saleRepository.deleteById(id);
    }

    @Override
    public SaleDTO getSaleById(UUID id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Sale not found with id: %s", id)
                ));
        return saleMapper.toDto(sale);
    }

    @Override
    public List<SaleDTO> getAllSales() {
        List<Sale> sales = saleRepository.findAll();
        return sales.stream()
                .map(saleMapper::toDto)
                .collect(Collectors.toList());
    }
}
