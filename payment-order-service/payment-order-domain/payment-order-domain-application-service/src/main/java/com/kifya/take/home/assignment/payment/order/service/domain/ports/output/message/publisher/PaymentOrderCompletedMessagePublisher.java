package com.kifya.take.home.assignment.payment.order.service.domain.ports.output.message.publisher;

import com.kifya.take.home.assignment.payment.order.service.domain.event.DomainEventPublisher;
import com.kifya.take.home.assignment.payment.order.service.domain.event.PaymentOrderCompletedEvent;

public interface PaymentOrderCompletedMessagePublisher extends DomainEventPublisher<PaymentOrderCompletedEvent> {
}
