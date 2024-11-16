package org.banta.citronix.dto.farm;

import lombok.Data;
import java.time.LocalDate;

@Data
public class FarmSearchCriteria {
    private String name;
    private String location;
    private Float minArea;
    private Float maxArea;
    private LocalDate establishedAfter;
    private LocalDate establishedBefore;
}