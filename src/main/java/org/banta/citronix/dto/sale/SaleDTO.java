package org.banta.citronix.dto.sale;

import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class SaleDTO {
    private UUID id;
    private LocalDate saleDate;
    private Double unitPrice;
    private String customer;
    private UUID harvestId;
    private Double revenue;
}