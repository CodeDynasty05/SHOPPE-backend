package com.matrix.SHOPPE.exception;

public class ProductNotFoundException extends ResourceNotFoundException {
    public ProductNotFoundException(String message) {
        super("ProductNotFoundException: "+message);
    }
}
