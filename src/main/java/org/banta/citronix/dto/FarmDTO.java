package org.banta.citronix.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.util.List;

@Data
public class FarmDTO {
    private String name;
    private String location;
    private Float area;
    private String dateEstablished;
    @JsonIgnore
    private List<FieldDTO> fields;
}
