package org.banta.citronix.web.errors.handler;

import jakarta.validation.ConstraintViolationException;
import org.banta.citronix.web.errors.constants.ErrorConstants;
import org.banta.citronix.web.errors.exception.BaseException;
import org.banta.citronix.web.errors.payload.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<Object> handleBaseException(BaseException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(ex.getStatus())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<ErrorResponse.ValidationError> validationErrors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            validationErrors.add(ErrorResponse.ValidationError.builder()
                    .object(fieldError.getObjectName())
                    .field(fieldError.getField())
                    .rejectedValue(fieldError.getRejectedValue())
                    .message(fieldError.getDefaultMessage())
                    .build()
            );
        });

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(ErrorConstants.ERR_VALIDATION)
                .debugMessage("Check 'errors' for details")
                .timestamp(LocalDateTime.now())
                .errors(validationErrors)
                .build();

        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        List<ErrorResponse.ValidationError> validationErrors = new ArrayList<>();
        ex.getConstraintViolations().forEach(violation -> {
            validationErrors.add(ErrorResponse.ValidationError.builder()
                    .object(violation.getRootBeanClass().getSimpleName())
                    .field(violation.getPropertyPath().toString())
                    .rejectedValue(violation.getInvalidValue())
                    .message(violation.getMessage())
                    .build()
            );
        });

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(ErrorConstants.ERR_VALIDATION)
                .debugMessage("Check 'errors' for details")
                .timestamp(LocalDateTime.now())
                .errors(validationErrors)
                .build();

        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Malformed JSON request")
                .debugMessage(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.CONFLICT)
                .message("Database error")
                .debugMessage(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'",
                        ex.getName(), ex.getValue(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName()))
                .debugMessage(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ErrorConstants.ERR_INTERNAL_SERVER)
                .debugMessage(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return buildResponseEntity(errorResponse);
    }

    private ResponseEntity<Object> buildResponseEntity(ErrorResponse errorResponse) {
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }
}