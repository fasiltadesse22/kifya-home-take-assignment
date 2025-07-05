package com.kifya.take.home.assignment.payment.order.service.domain.dto.message;

import com.kifya.take.home.assignment.payment.order.service.domain.valueobject.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Builder
@AllArgsConstructor
public class PaymentResponse {
    private String id;
    private String paymentOrderId;
    private String paymentId;
    private String customerId;
    private BigDecimal amount;
    private Instant createdAt;
    private PaymentStatus paymentStatus;
    private String failureReason;
}
