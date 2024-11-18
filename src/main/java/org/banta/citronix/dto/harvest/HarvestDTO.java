package org.banta.citronix.dto.harvest;

import lombok.Builder;
import lombok.Data;
import org.banta.citronix.domain.enums.Season;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class HarvestDTO {
    private UUID id;
    private Season season;
    private LocalDate harvestDate;
    private Double totalQuantity;
    private List<HarvestDetailDTO> harvestDetails;
}
