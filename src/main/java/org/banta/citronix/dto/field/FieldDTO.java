package org.banta.citronix.dto.field;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.banta.citronix.dto.tree.TreeDTO;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldDTO {
    public interface Create {};
    public interface Update {};

    private UUID id;

    @NotNull(message = "Area is required")
    @Min(value = 1000, message = "Field area must be at least 0.1 hectare (1000 m²)")
    @Max(value = Integer.MAX_VALUE, message = "Field area cannot exceed 50% of farm area") // Note: Actual validation done in service
    private Double area;

    @NotNull(message = "Farm ID is required", groups = {Create.class})
    private UUID farmId;

    @Valid
    @Size(max = 1000, message = "A field cannot have more than 1000 trees") // This should be calculated based on field area
    private List<TreeDTO> trees;
}