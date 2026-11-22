package com.wilfredo.courier_logistics_api.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    public record ApiErrorResponse(
            String status,
            String message,
            Map<String, String> errors

            @ExceptionHandler
    ){}

}
