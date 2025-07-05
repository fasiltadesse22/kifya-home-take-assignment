package com.kifya.take.home.assignment.payment.order.service.domain.ports.output.message.publisher;

import com.kifya.take.home.assignment.payment.order.service.domain.event.DomainEventPublisher;
import com.kifya.take.home.assignment.payment.order.service.domain.event.PaymentOrderCanceledEvent;

public interface PaymentOrderCanceledRequestMessagePublisher extends DomainEventPublisher<PaymentOrderCanceledEvent> {
}
