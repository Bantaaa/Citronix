package org.banta.citronix.dto.tree;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class TreeResponseDTO {
    private UUID id;
    private LocalDate datePlanted;
    private Long age;  // Calculated from datePlanted
    private String status;  // "YOUNG" (<3 years), "MATURE" (3-10 years), "OLD" (>10 years), "NON_PRODUCTIVE" (>20 years)
    private Double productivity;  // 2.5, 12, or 20 kg based on age
    private UUID fieldId;
    private Double seasonalProductivity;  // Same as productivity, but could be 0 if tree is non-productive
    private boolean isProductionPeriod;  // true if age < 20 years
}