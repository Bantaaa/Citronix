package org.banta.citronix.dto.field;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.banta.citronix.dto.tree.TreeRequestDTO;

import java.util.List;
import java.util.UUID;

@Data
public class FieldDTO {
    @NotNull(message = "Field ID cannot be null")
    private UUID id;

    @NotNull(message = "Area is required")
    @Min(value = 1000, message = "Field area must be at least 0.1 hectare (1000 m²)")
    @Max(value = 500000, message = "Field area cannot exceed 50 hectares")
    private Float area;

    @NotNull(message = "Farm ID is required")
    private UUID farmId;

    @Valid  // Validates each TreeDTO in the list
    @Size(max = 1000, message = "A field cannot have more than 1000 trees")  // 10 hectares max * 100 trees per hectare
    private List<TreeRequestDTO> trees;
}