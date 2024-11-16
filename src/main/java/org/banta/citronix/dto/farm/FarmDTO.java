package org.banta.citronix.dto.farm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.banta.citronix.dto.field.FieldDTO;

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
