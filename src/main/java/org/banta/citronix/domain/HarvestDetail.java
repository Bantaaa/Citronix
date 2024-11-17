package org.banta.citronix.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HarvestDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Harvest harvest;

    @ManyToOne
    private Tree tree;

    private Double quantity;
}