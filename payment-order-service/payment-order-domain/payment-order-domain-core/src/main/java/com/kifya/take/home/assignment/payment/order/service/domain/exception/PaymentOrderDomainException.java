package com.kifya.take.home.assignment.payment.order.service.domain.exception;

public class PaymentOrderDomainException extends RuntimeException {
    public PaymentOrderDomainException(String message) {
        super(message);
    }

    public PaymentOrderDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
