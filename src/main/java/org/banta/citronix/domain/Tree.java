package org.banta.citronix.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Planting date is required")
    private LocalDate datePlanted;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "Field is required")
    private Field field;
}