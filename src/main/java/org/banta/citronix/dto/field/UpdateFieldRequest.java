package org.banta.citronix.dto.field;
import jakarta.validation.constraints.Min;
import lombok.Data;
import java.util.UUID;


@Data
public class UpdateFieldRequest {
    @Min(value = 1000, message = "Field area must be at least 0.1 hectare (1000 m²)")
    private Float area;
}