package com.kifya.take.home.assignment.payment.order.service.domain.ports.output.repository;

import com.kifya.take.home.assignment.payment.order.service.domain.entity.PaymentOrder;
import com.kifya.take.home.assignment.payment.order.service.domain.valueobject.PaymentOrderId;

import java.util.Optional;
import java.util.UUID;

public interface PaymentOrderRepository {
    PaymentOrder save(PaymentOrder paymentOrder);
    Optional<PaymentOrder> findByPaymentOrderId(PaymentOrderId paymentOrderId);
    Optional<PaymentOrder> findByIdempotentKey(UUID idempotentKey);
}
