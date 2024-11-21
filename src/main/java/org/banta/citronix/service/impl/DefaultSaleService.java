package org.banta.citronix.service.impl;

import lombok.RequiredArgsConstructor;
import org.banta.citronix.domain.Sale;
import org.banta.citronix.dto.harvest.HarvestDTO;
import org.banta.citronix.dto.sale.SaleDTO;
import org.banta.citronix.mapper.HarvestMapper;
import org.banta.citronix.mapper.SaleMapper;
import org.banta.citronix.repository.SaleRepository;
import org.banta.citronix.service.HarvestService;
import org.banta.citronix.service.SaleService;
import org.banta.citronix.web.errors.exception.BadRequestException;
import org.banta.citronix.web.errors.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DefaultSaleService implements SaleService {
    private final SaleRepository saleRepository;
    private final HarvestService harvestService;
    private final SaleMapper saleMapper;

    @Override
    public SaleDTO createSale(SaleDTO saleDTO) {
        validateSale(saleDTO);

        HarvestDTO harvestDTO = harvestService.getHarvestById(saleDTO.getHarvestId());

        // Calculate revenue
        Double revenue = calculateRevenue(saleDTO.getUnitPrice(), harvestDTO.getTotalQuantity());
        saleDTO.setRevenue(revenue);

        Sale sale = saleMapper.toEntity(saleDTO);
        sale = saleRepository.save(sale);
        return saleMapper.toDto(sale);
    }

    @Override
    public void deleteSale(UUID id) {
        if (!saleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Sale not found with id: " + id);
        }
        saleRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public SaleDTO getSaleById(UUID id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found with id: " + id));
        return saleMapper.toDto(sale);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SaleDTO> getAllSales() {
        return saleRepository.findAll().stream()
                .map(saleMapper::toDto)
                .collect(Collectors.toList());
    }

    private void validateSale(SaleDTO saleDTO) {
        if (saleDTO.getUnitPrice() == null || saleDTO.getUnitPrice() <= 0) {
            throw new BadRequestException("Unit price must be greater than 0");
        }

        if (saleDTO.getHarvestId() == null) {
            throw new BadRequestException("Harvest ID is required");
        }
    }

    private Double calculateRevenue(Double unitPrice, Double quantity) {
        return unitPrice * quantity;
    }
}