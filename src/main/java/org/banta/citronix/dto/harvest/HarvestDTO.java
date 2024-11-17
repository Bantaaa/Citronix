package org.banta.citronix.dto.harvest;

import lombok.Builder;
import lombok.Data;
import org.banta.citronix.domain.enums.Season;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class HarvestDTO {
    private Long id;
    private Season season;
    private LocalDate harvestDate;
    private Double totalQuantity;
    private Long fieldId;
    private List<HarvestDetailDTO> harvestDetails;
}
