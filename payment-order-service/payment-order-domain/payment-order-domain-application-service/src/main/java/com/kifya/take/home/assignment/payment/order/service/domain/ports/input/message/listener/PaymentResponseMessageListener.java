package com.kifya.take.home.assignment.payment.order.service.domain.ports.input.message.listener;

import com.kifya.take.home.assignment.payment.order.service.domain.dto.message.PaymentResponse;

public interface PaymentResponseMessageListener {
    void paymentOrderCompleted(PaymentResponse paymentResponse);
    void paymentOrderCanceled(PaymentResponse paymentResponse);
}
