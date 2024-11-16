package org.banta.citronix.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Farm {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    //fields
    @OneToMany(mappedBy = "farm")
    private List<Field> fields;

    private String location;
    private Float area;
    private LocalDate dateEstablished;
}
