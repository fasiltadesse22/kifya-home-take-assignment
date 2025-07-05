package com.kifya.take.home.assignment.payment.order.service.domain.valueobject;

import java.util.UUID;

public class PaymentOrderId extends BaseId<UUID> {
    public PaymentOrderId(UUID value) {
        super(value);
    }
}