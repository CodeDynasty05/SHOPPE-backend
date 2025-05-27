package com.matrix.SHOPPE.exception;

public class PaymentException extends RuntimeException {
    public PaymentException(String message) {
        super("PaymentException: " + message);
    }
}
