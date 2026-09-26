package com.example.inventorymngt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,Object> notFound(ResourceNotFoundException e){return body(HttpStatus.NOT_FOUND,e.getMessage());}

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,Object> business(BusinessException e){return body(HttpStatus.BAD_REQUEST,e.getMessage());}

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,Object> illegal(IllegalArgumentException e){return body(HttpStatus.BAD_REQUEST,e.getMessage());}

    private Map<String,Object> body(HttpStatus status,String message){
        return Map.of("timestamp", LocalDateTime.now(), "status", status.value(), "error", message);
    }
}
