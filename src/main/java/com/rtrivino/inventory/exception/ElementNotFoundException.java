package com.rtrivino.inventory.exception;

/**
 * Exception thrown when a requested element cannot be found.
 *
 * <p>
 * This exception is used in the service layer to indicate that a resource
 * identified by a given id or business key does not exist in the database.
 * </p>
 */
public class ElementNotFoundException extends RuntimeException {

    public ElementNotFoundException(String message) {
        super(message);
    }
}
