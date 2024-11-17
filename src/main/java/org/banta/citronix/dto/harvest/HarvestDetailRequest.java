package org.banta.citronix.dto.harvest;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.UUID;

@Data
@Builder
public class HarvestDetailRequest {
    @NotNull(message = "Tree ID is required")
    private UUID treeId;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Double quantity;
}
