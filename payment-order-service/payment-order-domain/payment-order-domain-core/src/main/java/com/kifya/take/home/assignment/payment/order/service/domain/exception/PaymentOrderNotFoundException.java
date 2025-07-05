package com.kifya.take.home.assignment.payment.order.service.domain.exception;

public class PaymentOrderNotFoundException extends RuntimeException {
    public PaymentOrderNotFoundException(String message) {
        super(message);
    }

    public PaymentOrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
