package org.banta.citronix.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Farm {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String location;
    private Float area;
}
