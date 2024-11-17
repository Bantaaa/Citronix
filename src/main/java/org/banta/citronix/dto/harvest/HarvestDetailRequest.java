package org.banta.citronix.dto.harvest;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@Builder
public class HarvestDetailRequest {
    @NotNull(message = "Tree ID is required")
    private Long treeId;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Double quantity;
}
