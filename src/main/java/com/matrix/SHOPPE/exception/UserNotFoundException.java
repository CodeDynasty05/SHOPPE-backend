package com.matrix.SHOPPE.exception;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(String message) {
        super("UserNotFoundException: "+message);
    }
}
