package org.banta.citronix.dto.tree;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreeDTO {
    public interface Create {}
    public interface Update {}

    private UUID id;

    @NotNull(message = "Planting date is required", groups = Create.class)
    @PastOrPresent(message = "Planting date cannot be in the future", groups = {Create.class, Update.class})
    private LocalDate datePlanted;

    @NotNull(message = "Field ID is required", groups = Create.class)
    private UUID fieldId;

    // Calculated fields
    private Long age;
    private String status;
    private Double productivity;
    private Double seasonalProductivity;
    private Boolean isProductionPeriod;
}