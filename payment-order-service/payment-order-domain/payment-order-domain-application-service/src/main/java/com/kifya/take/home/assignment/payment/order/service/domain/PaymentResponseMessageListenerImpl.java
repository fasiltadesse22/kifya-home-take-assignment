package com.kifya.take.home.assignment.payment.order.service.domain;

import com.kifya.take.home.assignment.payment.order.service.domain.dto.message.PaymentResponse;
import com.kifya.take.home.assignment.payment.order.service.domain.entity.PaymentOrder;
import com.kifya.take.home.assignment.payment.order.service.domain.event.PaymentOrderCanceledEvent;
import com.kifya.take.home.assignment.payment.order.service.domain.event.PaymentOrderCompletedEvent;
import com.kifya.take.home.assignment.payment.order.service.domain.exception.PaymentOrderNotFoundException;
import com.kifya.take.home.assignment.payment.order.service.domain.ports.input.message.listener.PaymentResponseMessageListener;
import com.kifya.take.home.assignment.payment.order.service.domain.ports.output.message.publisher.PaymentOrderCanceledRequestMessagePublisher;
import com.kifya.take.home.assignment.payment.order.service.domain.ports.output.message.publisher.PaymentOrderCompletedMessagePublisher;
import com.kifya.take.home.assignment.payment.order.service.domain.ports.output.repository.PaymentOrderRepository;
import com.kifya.take.home.assignment.payment.order.service.domain.valueobject.PaymentOrderId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Validated
@Service
public class PaymentResponseMessageListenerImpl implements PaymentResponseMessageListener {

    private final PaymentOrderDomainService paymentOrderDomainService;
    private final PaymentOrderRepository paymentOrderRepository;
    private final PaymentOrderCompletedMessagePublisher paymentOrderCompletedMessagePublisher;
    private final PaymentOrderCanceledRequestMessagePublisher paymentOrderCanceledRequestMessagePublisher;

    public PaymentResponseMessageListenerImpl(PaymentOrderDomainService paymentOrderDomainService,
                                              PaymentOrderRepository paymentOrderRepository,
                                              PaymentOrderCompletedMessagePublisher paymentOrderCompletedMessagePublisher,
                                              PaymentOrderCanceledRequestMessagePublisher paymentOrderCanceledRequestMessagePublisher) {
        this.paymentOrderDomainService = paymentOrderDomainService;
        this.paymentOrderRepository = paymentOrderRepository;
        this.paymentOrderCompletedMessagePublisher = paymentOrderCompletedMessagePublisher;
        this.paymentOrderCanceledRequestMessagePublisher = paymentOrderCanceledRequestMessagePublisher;
    }

    @Override
    public void paymentOrderCompleted(PaymentResponse paymentResponse) {
        log.info("Completing payment for payment-order with id {}", paymentResponse.getPaymentOrderId());

        PaymentOrder paymentOrder = findPaymentOrder(paymentResponse.getPaymentOrderId());
        PaymentOrderCompletedEvent paymentOrderCompletedEvent = paymentOrderDomainService.completePaymentOrder(paymentOrder);
        paymentOrderRepository.save(paymentOrder);

        log.info("Publishing PaymentOrderCompletedEvent for order id: {}", paymentResponse.getPaymentOrderId());

        paymentOrderCompletedMessagePublisher.publish(paymentOrderCompletedEvent);
    }

    @Override
    public void paymentOrderCanceled(PaymentResponse paymentResponse) {
        log.info("Canceling payment for payment-order with id {}", paymentResponse.getPaymentOrderId());

        PaymentOrder paymentOrder = findPaymentOrder(paymentResponse.getPaymentOrderId());
        PaymentOrderCanceledEvent paymentOrderCanceledEvent = paymentOrderDomainService.cancelPaymentOrder(paymentOrder, paymentResponse.getFailureReason());
        paymentOrderRepository.save(paymentOrder);

        log.info("Publishing PaymentOrderCanceledEvent for order id: {}", paymentResponse.getPaymentOrderId());

        paymentOrderCanceledRequestMessagePublisher.publish(paymentOrderCanceledEvent);
    }

    private PaymentOrder findPaymentOrder(String paymentOrderId) {
        Optional<PaymentOrder> optionalPaymentOrderResult = paymentOrderRepository.findByPaymentOrderId(new PaymentOrderId(UUID.fromString(paymentOrderId)));

        if (optionalPaymentOrderResult.isEmpty()) {
            log.error("Payment order with id {} not found", paymentOrderId);
            throw new PaymentOrderNotFoundException("Payment order with id " +  paymentOrderId + " not found");
        }

        return optionalPaymentOrderResult.get();
    }
}
