package org.banta.citronix.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.banta.citronix.domain.enums.Season;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Harvest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Season season;

    @OneToMany(mappedBy = "harvest", cascade = CascadeType.ALL)
    private List<HarvestDetail> harvestDetails;

    private LocalDate harvestDate;

    private Double totalQuantity;
}