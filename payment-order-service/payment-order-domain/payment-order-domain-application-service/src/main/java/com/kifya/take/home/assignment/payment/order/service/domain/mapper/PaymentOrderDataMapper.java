package com.kifya.take.home.assignment.payment.order.service.domain.mapper;

import com.kifya.take.home.assignment.payment.order.service.domain.dto.create.CreatePaymentOrderCommand;
import com.kifya.take.home.assignment.payment.order.service.domain.dto.create.CreatePaymentOrderResponse;
import com.kifya.take.home.assignment.payment.order.service.domain.dto.track.TrackPaymentOrderResponse;
import com.kifya.take.home.assignment.payment.order.service.domain.entity.PaymentOrder;
import com.kifya.take.home.assignment.payment.order.service.domain.valueobject.CustomerId;
import com.kifya.take.home.assignment.payment.order.service.domain.valueobject.Money;
import org.springframework.stereotype.Component;

@Component
public class PaymentOrderDataMapper {

    public PaymentOrder createPaymentOrderCommandToPaymentOrder(CreatePaymentOrderCommand createPaymentOrderCommand) {
        return PaymentOrder.builder()
                .customerId(new CustomerId(createPaymentOrderCommand.getCustomerId()))
                .amount(new Money(createPaymentOrderCommand.getAmount()))
                .idempotentKey(createPaymentOrderCommand.getIdempotentKey())
                .build();
    }


    public CreatePaymentOrderResponse paymentOrderToCreatePaymentOrderResponse(PaymentOrder paymentOrder, String message) {
        return CreatePaymentOrderResponse.builder()
                .paymentOrderId(paymentOrder.getId().getValue())
                .paymentOrderStatus(paymentOrder.getPaymentOrderStatus())
                .message(message)
                .build();
    }

    public TrackPaymentOrderResponse paymentOrderToTrackPaymentOrderResponse(PaymentOrder paymentOrder) {
        return TrackPaymentOrderResponse.builder()
                .paymentOrderId(paymentOrder.getId().getValue())
                .paymentOrderStatus(paymentOrder.getPaymentOrderStatus())
                .failureReason(paymentOrder.getFailureReason())
                .build();
    }
}
