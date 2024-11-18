package org.banta.citronix.domain;

import jakarta.persistence.*;
import lombok.*;
import org.banta.citronix.domain.enums.Season;

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
public class Harvest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Season season;

    @OneToMany(mappedBy = "harvest", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<HarvestDetail> harvestDetails = new ArrayList<>();

    private LocalDate harvestDate;

    private Double totalQuantity;

    // Helper methods to manage the relationship
    public void addHarvestDetail(HarvestDetail detail) {
        harvestDetails.add(detail);
        detail.setHarvest(this);
    }

    public void removeHarvestDetail(HarvestDetail detail) {
        harvestDetails.remove(detail);
        detail.setHarvest(null);
    }

    public void setHarvestDetails(List<HarvestDetail> details) {
        this.harvestDetails.clear();
        if (details != null) {
            details.forEach(this::addHarvestDetail);
        }
    }
}