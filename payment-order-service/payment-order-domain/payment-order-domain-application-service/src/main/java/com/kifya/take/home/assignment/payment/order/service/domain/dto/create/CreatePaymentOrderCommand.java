package com.kifya.take.home.assignment.payment.order.service.domain.dto.create;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CreatePaymentOrderCommand {
    @NotNull
    private final UUID customerId;
    @NotNull
    private final BigDecimal amount;
    @NotNull
    private final UUID idempotentKey;
}
