package org.banta.citronix.dto.farm;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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

    @NotBlank(message = "Farm name is required")
    private String name;

    @NotBlank(message = "Farm location is required")
    private String location;

    @NotNull(message = "Farm area is required")
    @Min(value = 2000, message = "Farm area must be at least 0.2 hectare (2000 m²)")
    private Float area;

    @NotNull(message = "Date established is required")
    @PastOrPresent(message = "Establishment date cannot be in the future")
    private LocalDate dateEstablished;

    @Builder.Default
    private List<FieldDTO> fields = new ArrayList<>();
}