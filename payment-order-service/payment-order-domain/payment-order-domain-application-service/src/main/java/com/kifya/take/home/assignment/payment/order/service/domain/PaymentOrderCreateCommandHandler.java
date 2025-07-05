package com.kifya.take.home.assignment.payment.order.service.domain;

import com.kifya.take.home.assignment.payment.order.service.domain.dto.create.CreatePaymentOrderCommand;
import com.kifya.take.home.assignment.payment.order.service.domain.dto.create.CreatePaymentOrderResponse;
import com.kifya.take.home.assignment.payment.order.service.domain.event.PaymentOrderCreatedEvent;
import com.kifya.take.home.assignment.payment.order.service.domain.mapper.PaymentOrderDataMapper;
import com.kifya.take.home.assignment.payment.order.service.domain.ports.output.message.publisher.PaymentOrderCreatedRequestMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentOrderCreateCommandHandler {

    private final PaymentOrderCreateHelper paymentOrderCreateHelper;
    private final PaymentOrderDataMapper paymentOrderDataMapper;
    private final PaymentOrderCreatedRequestMessagePublisher paymentOrderCreatedRequestMessagePublisher;

    public PaymentOrderCreateCommandHandler(PaymentOrderCreateHelper paymentOrderCreateHelper,
                                            PaymentOrderDataMapper paymentOrderDataMapper,
                                            PaymentOrderCreatedRequestMessagePublisher paymentOrderCreatedRequestMessagePublisher) {
        this.paymentOrderCreateHelper = paymentOrderCreateHelper;
        this.paymentOrderDataMapper = paymentOrderDataMapper;
        this.paymentOrderCreatedRequestMessagePublisher = paymentOrderCreatedRequestMessagePublisher;
    }

    public CreatePaymentOrderResponse createPaymentOrder(CreatePaymentOrderCommand createPaymentOrderCommand) {
        PaymentOrderCreatedEvent paymentOrderCreatedEvent = paymentOrderCreateHelper.persistPaymentOrder(createPaymentOrderCommand);
        log.info("Payment order created with id: {}", paymentOrderCreatedEvent.getPaymentOrder().getId().getValue());

        paymentOrderCreatedRequestMessagePublisher.publish(paymentOrderCreatedEvent);

        return paymentOrderDataMapper.paymentOrderToCreatePaymentOrderResponse(paymentOrderCreatedEvent.getPaymentOrder(), "Payment order successfully created");
    }

}
