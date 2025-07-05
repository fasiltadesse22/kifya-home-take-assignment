package com.kifya.take.home.assignment.payment.order.service.domain;

import com.kifya.take.home.assignment.payment.order.service.domain.dto.create.CreatePaymentOrderCommand;
import com.kifya.take.home.assignment.payment.order.service.domain.entity.PaymentOrder;
import com.kifya.take.home.assignment.payment.order.service.domain.event.PaymentOrderCreatedEvent;
import com.kifya.take.home.assignment.payment.order.service.domain.exception.PaymentOrderDomainException;
import com.kifya.take.home.assignment.payment.order.service.domain.mapper.PaymentOrderDataMapper;
import com.kifya.take.home.assignment.payment.order.service.domain.ports.output.repository.PaymentOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class PaymentOrderCreateHelper {

    private final PaymentOrderDomainService paymentOrderDomainService;
    private final PaymentOrderRepository paymentOrderRepository;
    private final PaymentOrderDataMapper paymentOrderDataMapper;

    public PaymentOrderCreateHelper(PaymentOrderDomainService paymentOrderDomainService,
                             PaymentOrderRepository paymentOrderRepository,
                             PaymentOrderDataMapper paymentOrderDataMapper) {
        this.paymentOrderDomainService = paymentOrderDomainService;
        this.paymentOrderRepository = paymentOrderRepository;
        this.paymentOrderDataMapper = paymentOrderDataMapper;
    }

    @Transactional
    public PaymentOrderCreatedEvent persistPaymentOrder(CreatePaymentOrderCommand createPaymentOrderCommand) {

        if (!isIdempotentRequest(createPaymentOrderCommand)) {
            log.error("Payment order already initiated");
            throw new PaymentOrderDomainException("Payment order already initiated");
        }

        PaymentOrder paymentOrder = paymentOrderDataMapper.createPaymentOrderCommandToPaymentOrder(createPaymentOrderCommand);
        PaymentOrderCreatedEvent paymentOrderCreatedEvent = paymentOrderDomainService.validateAndInitiatePaymentOrder(paymentOrder);
        savePaymentOrder(paymentOrder);

        log.info("Payment order created with id {}", paymentOrder.getId().getValue());

        return paymentOrderCreatedEvent;
    }

    private PaymentOrder savePaymentOrder(PaymentOrder paymentOrder) {
        PaymentOrder paymentOrderResult = paymentOrderRepository.save(paymentOrder);

        if (paymentOrderResult == null) {
            log.error("Payment order could not be saved");
            throw new PaymentOrderDomainException("Payment order could not be saved");
        }

        return paymentOrderResult;
    }

    private boolean isIdempotentRequest(CreatePaymentOrderCommand createPaymentOrderCommand) {
        return paymentOrderRepository
                .findByIdempotentKey(createPaymentOrderCommand.getIdempotentKey())
                .isEmpty();
    }
}
