package org.banta.citronix.dto.field;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CreateFieldRequest {
    @NotNull(message = "Area is required")
    @Min(value = 1000, message = "Field area must be at least 0.1 hectare (1000 m²)")
    private Float area;

    @NotNull(message = "Farm ID is required")
    private UUID farmId;
}
