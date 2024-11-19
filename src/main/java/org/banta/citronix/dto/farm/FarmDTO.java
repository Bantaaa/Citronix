package org.banta.citronix.dto.farm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.banta.citronix.dto.field.FieldDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FarmDTO {
    private UUID id;
    private String name;
    private String location;
    private Float area;
    private LocalDate dateEstablished;
    @Builder.Default
    private List<FieldDTO> fields = new ArrayList<>();
}
