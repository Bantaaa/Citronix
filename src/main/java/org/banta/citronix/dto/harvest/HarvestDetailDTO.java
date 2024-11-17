package org.banta.citronix.dto.harvest;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HarvestDetailDTO {
    private UUID id;
    private UUID harvestId;
    private UUID treeId;
    private Double quantity;

    // Additional fields for better context in responses
    private Integer treeAge;
    private Double expectedProductivity;
    private String productivityCategory; // "YOUNG", "MATURE", "OLD", "NON_PRODUCTIVE"
}
