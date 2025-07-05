package com.kifya.take.home.assignment.order.service.dataaccess.paymentorder.entity;

import com.kifya.take.home.assignment.payment.order.service.domain.valueobject.PaymentOrderStatus;
import lombok.*;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_orders")
@Entity
public class PaymentOrderEntity {
    @Id
    private UUID id;
    private UUID customerId;
    private UUID idempotentKey;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private PaymentOrderStatus paymentOrderStatus;
    private String failureReason;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentOrderEntity that = (PaymentOrderEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
