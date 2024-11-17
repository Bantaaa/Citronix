package org.banta.citronix.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Farm {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Farm name is required")
    private String name;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Field> fields = new ArrayList<>();

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Area is required")
    @Min(value = 2000, message = "Farm area must be at least 0.2 hectare")
    private Float area;

    @NotNull(message = "Establishment date is required")
    @PastOrPresent(message = "Establishment date cannot be in the future")
    private LocalDate dateEstablished;
}