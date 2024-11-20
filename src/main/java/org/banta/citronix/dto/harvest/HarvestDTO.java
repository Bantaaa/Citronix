package org.banta.citronix.dto.harvest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.banta.citronix.domain.enums.Season;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HarvestDTO {
    private UUID id;

    @NotNull(message = "Season is required")
    private Season season;

    @NotNull(message = "Harvest date is required")
    @PastOrPresent(message = "Harvest date cannot be in the future")
    private LocalDate harvestDate;

    private Double totalQuantity; // Calculated field

    @NotEmpty(message = "Harvest details are required")
    @Valid
    private List<HarvestDetailDTO> harvestDetails;
}
