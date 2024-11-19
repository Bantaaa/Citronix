package org.banta.citronix.dto.sale;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Getter
@Setter
public class SaleDTO {
    private UUID id;
    private LocalDate saleDate;
    private Double unitPrice;
    private String customer;
    private UUID harvestId;
    private Double revenue;
}