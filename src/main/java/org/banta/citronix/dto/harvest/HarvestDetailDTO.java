package org.banta.citronix.dto.harvest;

import lombok.*;

import java.util.UUID;

@Data
@Builder
public class HarvestDetailDTO {
    private UUID id;
    private UUID treeId;
    private Double quantity;
}
