package com.kifya.take.home.assignment.payment.order.service.domain.event;

import com.kifya.take.home.assignment.payment.order.service.domain.entity.PaymentOrder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public abstract class PaymentOrderEvent implements DomainEvent<PaymentOrder> {
    private final PaymentOrder paymentOrder;
    private final ZonedDateTime createdAt;

    public PaymentOrderEvent(PaymentOrder paymentOrder, ZonedDateTime createdAt) {
        this.paymentOrder = paymentOrder;
        this.createdAt = createdAt;
    }
}
