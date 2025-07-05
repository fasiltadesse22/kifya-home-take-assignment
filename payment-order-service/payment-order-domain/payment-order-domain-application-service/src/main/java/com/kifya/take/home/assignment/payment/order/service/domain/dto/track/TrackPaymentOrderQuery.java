package com.kifya.take.home.assignment.payment.order.service.domain.dto.track;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class TrackPaymentOrderQuery {
    @NotNull
    private final UUID paymentOrderId;
}
