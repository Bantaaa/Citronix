package org.banta.citronix.dto;

import lombok.Data;

import java.util.List;

@Data
public class FieldDTO {
    private String name;
    private String location;
    private Float area;
    private String dateEstablished;
    private List<TreeDTO> plants;
}
