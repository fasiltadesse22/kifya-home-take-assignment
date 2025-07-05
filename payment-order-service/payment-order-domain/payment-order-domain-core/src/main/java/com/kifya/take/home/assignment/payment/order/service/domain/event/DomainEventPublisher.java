package com.kifya.take.home.assignment.payment.order.service.domain.event;

public interface DomainEventPublisher<T extends DomainEvent> {

    void publish(T domainEvent);
}
