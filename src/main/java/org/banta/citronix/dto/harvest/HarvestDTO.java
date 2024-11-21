package org.banta.citronix.dto.harvest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.banta.citronix.domain.enums.Season;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HarvestDTO {
    public interface Update {};

    @NotNull(message = "Harvest ID is required", groups = Update.class)
    private UUID id;

    @JsonIgnore
    private Season season;

    @NotNull(message = "Harvest date is required")
    @PastOrPresent(message = "Harvest date cannot be in the future")
    private LocalDate harvestDate;

    private Double totalQuantity; // Calculated field

    @NotEmpty(message = "Harvest details are required")
    @Valid
    private List<HarvestDetailDTO> harvestDetails;
}
