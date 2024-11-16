package org.banta.citronix.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Tree {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private LocalDate datePlanted;
    @ManyToOne
    private Farm farm;
    @ManyToOne
    private Field field;
}
