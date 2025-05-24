package com.matrix.SHOPPE.exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super("ForbiddenException: "+message);
    }
}
