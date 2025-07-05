package com.kifya.take.home.assignment.payment.order.service.domain;

import com.kifya.take.home.assignment.payment.order.service.domain.entity.PaymentOrder;
import com.kifya.take.home.assignment.payment.order.service.domain.event.PaymentOrderCanceledEvent;
import com.kifya.take.home.assignment.payment.order.service.domain.event.PaymentOrderCompletedEvent;
import com.kifya.take.home.assignment.payment.order.service.domain.event.PaymentOrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
public class PaymentOrderDomainServiceImpl implements PaymentOrderDomainService {

    private static final String UTC = "UTC";

    @Override
    public PaymentOrderCreatedEvent validateAndInitiatePaymentOrder(PaymentOrder paymentOrder) {
        paymentOrder.validatePaymentOrder();
        paymentOrder.initializePaymentOrder();

        log.info("Payment order id: {} is initialized", paymentOrder.getId().getValue());

        return new PaymentOrderCreatedEvent(paymentOrder, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public PaymentOrderCompletedEvent completePaymentOrder(PaymentOrder paymentOrder) {
        paymentOrder.completePaymentOrder();

        log.info("Payment order id: {} is completed", paymentOrder.getId().getValue());

        return new PaymentOrderCompletedEvent(paymentOrder, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public PaymentOrderCanceledEvent cancelPaymentOrder(PaymentOrder paymentOrder, String failureReason) {
        paymentOrder.cancelPaymentOrder(failureReason);

        log.info("Payment order id: {} is cancelled", paymentOrder.getId().getValue());

        return new PaymentOrderCanceledEvent(paymentOrder, ZonedDateTime.now(ZoneId.of(UTC)));
    }
}
