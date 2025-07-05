package com.kifya.take.home.assignment.payment.order.service.messaging.mapper;

import com.kifya.take.home.assignment.kafka.order.avro.model.PaymentOrderStatus;
import com.kifya.take.home.assignment.kafka.order.avro.model.PaymentRequestAvroModel;
import com.kifya.take.home.assignment.kafka.order.avro.model.PaymentResponseAvroModel;
import com.kifya.take.home.assignment.payment.order.service.domain.dto.message.PaymentResponse;
import com.kifya.take.home.assignment.payment.order.service.domain.entity.PaymentOrder;
import com.kifya.take.home.assignment.payment.order.service.domain.event.PaymentOrderCanceledEvent;
import com.kifya.take.home.assignment.payment.order.service.domain.event.PaymentOrderCompletedEvent;
import com.kifya.take.home.assignment.payment.order.service.domain.event.PaymentOrderCreatedEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentOrderMessagingDataMapper {

    public PaymentResponse paymentResponseAvroModelToPaymentResponse(PaymentResponseAvroModel
                                                                             paymentResponseAvroModel) {
        return PaymentResponse.builder()
                .id(paymentResponseAvroModel.getId())
                .paymentId(paymentResponseAvroModel.getPaymentId())
                .customerId(paymentResponseAvroModel.getCustomerId())
                .paymentOrderId(paymentResponseAvroModel.getPaymentOrderId())
                .amount(paymentResponseAvroModel.getAmount())
                .createdAt(paymentResponseAvroModel.getCreatedAt())
                .paymentStatus(com.kifya.take.home.assignment.payment.order.service.domain.valueobject.PaymentStatus.valueOf(
                        paymentResponseAvroModel.getPaymentStatus().name()))
                .failureReason(paymentResponseAvroModel.getFailureMessages())
                .build();
    }

    public PaymentRequestAvroModel orderCreatedEventToPaymentRequestAvroModel(PaymentOrderCreatedEvent paymentOrderCreatedEvent) {

        PaymentOrder paymentOrder = paymentOrderCreatedEvent.getPaymentOrder();

        return PaymentRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setCustomerId(paymentOrder.getCustomerId().getValue().toString())
                .setPaymentOrderId(paymentOrder.getId().getValue().toString())
                .setAmount(paymentOrder.getAmount().getAmount())
                .setPaymentOrderStatus(PaymentOrderStatus.PENDING)
                .setCreatedAt(paymentOrderCreatedEvent.getCreatedAt().toInstant())
                .build();
    }

    public PaymentRequestAvroModel orderCanceledEventToPaymentRequestAvroModel(PaymentOrderCanceledEvent paymentOrderCanceledEvent) {

        PaymentOrder paymentOrder = paymentOrderCanceledEvent.getPaymentOrder();

        return PaymentRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setCustomerId(paymentOrder.getCustomerId().getValue().toString())
                .setPaymentOrderId(paymentOrder.getId().getValue().toString())
                .setAmount(paymentOrder.getAmount().getAmount())
                .setPaymentOrderStatus(PaymentOrderStatus.CANCELED)
                .setCreatedAt(paymentOrderCanceledEvent.getCreatedAt().toInstant())
                .build();
    }

    public PaymentRequestAvroModel orderCanceledEventToPaymentRequestAvroModel(PaymentOrderCompletedEvent paymentOrderCompletedEvent) {

        PaymentOrder paymentOrder = paymentOrderCompletedEvent.getPaymentOrder();

        return PaymentRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setCustomerId(paymentOrder.getCustomerId().getValue().toString())
                .setPaymentOrderId(paymentOrder.getId().getValue().toString())
                .setAmount(paymentOrder.getAmount().getAmount())
                .setPaymentOrderStatus(PaymentOrderStatus.COMPLETED)
                .setCreatedAt(paymentOrderCompletedEvent.getCreatedAt().toInstant())
                .build();
    }
}
