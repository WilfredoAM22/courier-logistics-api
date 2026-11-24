package com.wilfredo.courier_logistics_api.exception;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.stream.Collectors;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler{
public record ApiErrorResponse(
            String status,
            String message,
            Map<String, String> errors
    ){}
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        error -> error.getDefaultMessage() != null ? error.getDefaultMessage() : "Valor inválido",
                        (existing, replacement) -> existing
                ));
        return ResponseEntity.badRequest()
                .body(new ApiErrorResponse("validation_error", "Error de validación en los datos enviados", fieldErrors));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleUnreadableMessage(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest()
                .body(new ApiErrorResponse(
                        "invalid_request",
                        "Formato de solicitud inválido o categoría/estado no reconocido",
                        Map.of()
                ));
    }
@ExceptionHandler(ShipmentNotFoundException.class)
public ResponseEntity<ApiErrorResponse> handleNotFound(ShipmentNotFoundException ex){
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiErrorResponse
            ("not_found", ex.getMessage(), Map.of()));

}
@ExceptionHandler(InvalidStatusTransitionException.class)
public ResponseEntity<ApiErrorResponse> handleInvalidTransition(InvalidStatusTransitionException ex){
    return ResponseEntity.status(HttpStatus.CONFLICT).body
            (new ApiErrorResponse("conflict", ex.getMessage(), Map.of()));

}
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest()
                .body(new ApiErrorResponse("invalid_request", ex.getMessage(), Map.of()));
    }

}