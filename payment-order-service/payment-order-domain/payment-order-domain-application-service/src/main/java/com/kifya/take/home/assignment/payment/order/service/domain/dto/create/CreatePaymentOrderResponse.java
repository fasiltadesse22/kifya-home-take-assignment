package com.kifya.take.home.assignment.payment.order.service.domain.dto.create;

import com.kifya.take.home.assignment.payment.order.service.domain.valueobject.PaymentOrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CreatePaymentOrderResponse {
    @NotNull
    private final UUID paymentOrderId;
    @NotNull
    private final PaymentOrderStatus paymentOrderStatus;
    @NotNull
    private final String message;
}
