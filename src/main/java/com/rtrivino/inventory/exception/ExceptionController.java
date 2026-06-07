package com.rtrivino.inventory.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler({ElementNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> elementNotFound(Exception ex){
        Map<String, Object> error = new HashMap<>();
        
        error.put("fecha", new Date());
        error.put("message", ex.getMessage());
        error.put("error", "Error en servicios API");
        error.put("status", HttpStatus.NOT_FOUND.value());

        return error;
    }

    @ExceptionHandler({NoResourceFoundException.class})
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public Map<String, Object> methodNotFound(Exception ex){
        Map<String, Object> error = new HashMap<>();
        
        error.put("fecha", new Date());
        error.put("message", ex.getMessage());
        error.put("error", "Error en servicios API, posiblemente no exista el servicio HTTP, verifique por favor");
        error.put("status", HttpStatus.NOT_FOUND.value());

        return error;
    }
}
