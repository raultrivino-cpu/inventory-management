package com.rtrivino.inventory.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * Global exception handler for REST controllers.
 *
 * <p>
 * This component centralizes API error handling and transforms exceptions
 * thrown by controllers or services into consistent HTTP responses.
 * </p>
 *
 * <p>
 * It is responsible for handling scenarios such as resource not found,
 * invalid request payloads and validation errors, preventing raw stack traces
 * from being exposed to API consumers.
 * </p>
 */
@RestControllerAdvice
public class ExceptionController {

    /**
     * Handles not-found errors raised from the service layer.
     *
     * @param ex exception containing the not-found message
     * @return error response with HTTP 404 status
     */
    @ExceptionHandler({ ElementNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> elementNotFound(Exception ex) {
        Map<String, Object> error = new HashMap<>();

        error.put("fecha", new Date());
        error.put("message", ex.getMessage());
        error.put("error", "Error en servicios API");
        error.put("status", HttpStatus.NOT_FOUND.value());

        return error;
    }

    /**
     * Handles bean validation errors produced when request bodies do not satisfy
     * the declared validation constraints.
     *
     * @param ex validation exception raised by Spring
     * @return map containing field names and validation messages
     */
    @ExceptionHandler({ NoResourceFoundException.class })
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public Map<String, Object> methodNotFound(Exception ex) {
        Map<String, Object> error = new HashMap<>();

        error.put("fecha", new Date());
        error.put("message", ex.getMessage());
        error.put("error", "Error en servicios API, posiblemente no exista el servicio HTTP, verifique por favor");
        error.put("status", HttpStatus.NOT_FOUND.value());

        return error;
    }

    /**
     * Handles invalid argument errors raised during request processing.
     *
     * @param ex exception containing the validation message
     * @return error response with HTTP 400 status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, Object> error = new HashMap<>();

        error.put("fecha", new Date());
        error.put("message", ex.getMessage());
        error.put("error", "Error en servicios API, posiblemente los datos de la solicitud deben verificarse");
        error.put("status", HttpStatus.NOT_FOUND.value());

        return error;
    }
}
