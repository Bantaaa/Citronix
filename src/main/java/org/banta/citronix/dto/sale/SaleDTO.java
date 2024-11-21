package org.banta.citronix.dto.sale;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTO {
    private UUID id;

    @NotNull(message = "Sale date is required")
    @PastOrPresent(message = "Sale date cannot be in the future")
    private LocalDate saleDate;

    @NotNull(message = "Unit price is required")
    @Positive(message = "Unit price must be greater than 0")
    private Double unitPrice;

    @NotBlank(message = "Customer name is required")
    private String customer;

    @NotNull(message = "Harvest ID is required")
    private UUID harvestId;

    private Double revenue; // Calculated field
}