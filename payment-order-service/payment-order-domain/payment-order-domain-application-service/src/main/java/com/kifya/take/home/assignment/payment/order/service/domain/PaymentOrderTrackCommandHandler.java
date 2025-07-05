package com.kifya.take.home.assignment.payment.order.service.domain;

import com.kifya.take.home.assignment.payment.order.service.domain.dto.track.TrackPaymentOrderQuery;
import com.kifya.take.home.assignment.payment.order.service.domain.dto.track.TrackPaymentOrderResponse;
import com.kifya.take.home.assignment.payment.order.service.domain.entity.PaymentOrder;
import com.kifya.take.home.assignment.payment.order.service.domain.exception.PaymentOrderNotFoundException;
import com.kifya.take.home.assignment.payment.order.service.domain.mapper.PaymentOrderDataMapper;
import com.kifya.take.home.assignment.payment.order.service.domain.ports.output.repository.PaymentOrderRepository;
import com.kifya.take.home.assignment.payment.order.service.domain.valueobject.PaymentOrderId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
public class PaymentOrderTrackCommandHandler {

    private final PaymentOrderDataMapper paymentOrderDataMapper;
    private final PaymentOrderRepository paymentOrderRepository;

    public PaymentOrderTrackCommandHandler(PaymentOrderDataMapper paymentOrderDataMapper, PaymentOrderRepository paymentOrderRepository) {
        this.paymentOrderDataMapper = paymentOrderDataMapper;
        this.paymentOrderRepository = paymentOrderRepository;
    }

    @Transactional(readOnly = true)
    public TrackPaymentOrderResponse trackPaymentOrder(TrackPaymentOrderQuery trackPaymentOrderQuery) {

        Optional<PaymentOrder> optionalPaymentOrderResult = paymentOrderRepository.findByPaymentOrderId(new PaymentOrderId(trackPaymentOrderQuery.getPaymentOrderId()));

        if (optionalPaymentOrderResult.isEmpty()) {
            log.warn("Could not find payment order with id {}", trackPaymentOrderQuery.getPaymentOrderId());
            throw new PaymentOrderNotFoundException("Could not find payment order with id " + trackPaymentOrderQuery.getPaymentOrderId());
        }

        return paymentOrderDataMapper.paymentOrderToTrackPaymentOrderResponse(optionalPaymentOrderResult.get());
    }
}
