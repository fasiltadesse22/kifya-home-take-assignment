package com.kifya.take.home.assignment.payment.order.service.domain;

import com.kifya.take.home.assignment.payment.order.service.domain.entity.PaymentOrder;
import com.kifya.take.home.assignment.payment.order.service.domain.event.PaymentOrderCanceledEvent;
import com.kifya.take.home.assignment.payment.order.service.domain.event.PaymentOrderCompletedEvent;
import com.kifya.take.home.assignment.payment.order.service.domain.event.PaymentOrderCreatedEvent;

public interface PaymentOrderDomainService {
    PaymentOrderCreatedEvent validateAndInitiatePaymentOrder(PaymentOrder paymentOrder);
    PaymentOrderCompletedEvent completePaymentOrder(PaymentOrder paymentOrder);
    PaymentOrderCanceledEvent cancelPaymentOrder(PaymentOrder paymentOrder, String failureReason);
}
