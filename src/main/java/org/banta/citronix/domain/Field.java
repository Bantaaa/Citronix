package org.banta.citronix.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Float area;
    @ManyToOne
    private Farm farm;
}
