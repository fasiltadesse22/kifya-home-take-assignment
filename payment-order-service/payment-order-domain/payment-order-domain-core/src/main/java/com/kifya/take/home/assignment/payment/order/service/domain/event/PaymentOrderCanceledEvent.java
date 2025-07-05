package com.kifya.take.home.assignment.payment.order.service.domain.event;

import com.kifya.take.home.assignment.payment.order.service.domain.entity.PaymentOrder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class PaymentOrderCanceledEvent extends PaymentOrderEvent {
    public PaymentOrderCanceledEvent(PaymentOrder paymentOrder, ZonedDateTime createdAt) {
        super(paymentOrder, createdAt);
    }
}
