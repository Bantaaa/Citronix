package org.banta.citronix.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Builder
@RequiredArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private LocalDate saleDate;
    private Double unitPrice;
    private String customer;
    @ManyToOne(fetch = FetchType.LAZY)
    private Harvest harvest;
}
