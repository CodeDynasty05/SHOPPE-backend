package com.matrix.SHOPPE.exception;

public class BasketNotFoundException extends ResourceNotFoundException {
    public BasketNotFoundException(String message) {
        super("BasketNotFoundException: "+message);
    }
}
