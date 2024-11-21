package org.banta.citronix.dto.harvest;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HarvestDetailDTO {
    private UUID id;

    @NotNull(message = "Tree ID is required")
    private UUID treeId;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be greater than 0")
    private Double quantity;
}
