package org.banta.citronix.dto.tree;

import lombok.Data;
import org.banta.citronix.dto.field.FieldDTO;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class TreeRequestDTO {
    private LocalDate datePlanted;
    private UUID fieldId;
}
