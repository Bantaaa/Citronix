package org.banta.citronix.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tree {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "Planting date is required")
    private LocalDate datePlanted;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "Field is required")
    private Field field;

    @OneToMany
    private List<HarvestDetail> harvests;
}