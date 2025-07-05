package com.kifya.take.home.assignment.order.service.dataaccess.paymentorder.mapper;

import com.kifya.take.home.assignment.order.service.dataaccess.paymentorder.entity.PaymentOrderEntity;
import com.kifya.take.home.assignment.payment.order.service.domain.entity.PaymentOrder;
import com.kifya.take.home.assignment.payment.order.service.domain.valueobject.CustomerId;
import com.kifya.take.home.assignment.payment.order.service.domain.valueobject.Money;
import com.kifya.take.home.assignment.payment.order.service.domain.valueobject.PaymentOrderId;
import org.springframework.stereotype.Component;

@Component
public class PaymentOrderDataAccessMapper {

    public PaymentOrderEntity paymentOrderToPaymentOrderEntity(PaymentOrder paymentOrder) {
        return PaymentOrderEntity.builder()
                .id(paymentOrder.getId().getValue())
                .customerId(paymentOrder.getCustomerId().getValue())
                .idempotentKey(paymentOrder.getIdempotentKey())
                .amount(paymentOrder.getAmount().getAmount())
                .paymentOrderStatus(paymentOrder.getPaymentOrderStatus())
                .failureReason(paymentOrder.getFailureReason())
                .build();
    }

    public PaymentOrder paymentOrderEntityToPaymentOrder(PaymentOrderEntity paymentOrderEntity) {
        return PaymentOrder.builder()
                .paymentOrderId(new PaymentOrderId(paymentOrderEntity.getId()))
                .customerId(new CustomerId(paymentOrderEntity.getCustomerId()))
                .amount(new Money(paymentOrderEntity.getAmount()))
                .idempotentKey(paymentOrderEntity.getIdempotentKey())
                .paymentOrderStatus(paymentOrderEntity.getPaymentOrderStatus())
                .failureMessage(paymentOrderEntity.getFailureReason())
                .build();
    }
}
