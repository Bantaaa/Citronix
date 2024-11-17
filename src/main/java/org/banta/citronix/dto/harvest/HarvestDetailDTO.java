package org.banta.citronix.dto.harvest;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HarvestDetailDTO {
    private Long id;
    private Long harvestId;
    private Long treeId;
    private Double quantity;

    // Additional fields for better context in responses
    private Integer treeAge;
    private Double expectedProductivity;
    private String productivityCategory; // "YOUNG", "MATURE", "OLD", "NON_PRODUCTIVE"
}
