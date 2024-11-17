package org.banta.citronix.web.errors.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ErrorResponse {
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;
    private List<ValidationError> errors;

    @Data
    @Builder
    public static class ValidationError {
        private String object;
        private String field;
        private Object rejectedValue;
        private String message;
    }
}