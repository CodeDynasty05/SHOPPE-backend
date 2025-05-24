package com.matrix.SHOPPE.exception;

public class BlogNotFoundException extends ResourceNotFoundException {
    public BlogNotFoundException(String message) {
        super("BlogNotFoundException: "+message);
    }
}
