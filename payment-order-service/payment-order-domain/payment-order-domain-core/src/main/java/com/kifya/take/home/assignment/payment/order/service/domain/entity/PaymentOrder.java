package com.kifya.take.home.assignment.payment.order.service.domain.entity;

import com.kifya.take.home.assignment.payment.order.service.domain.exception.PaymentOrderDomainException;
import com.kifya.take.home.assignment.payment.order.service.domain.valueobject.CustomerId;
import com.kifya.take.home.assignment.payment.order.service.domain.valueobject.Money;
import com.kifya.take.home.assignment.payment.order.service.domain.valueobject.PaymentOrderId;
import com.kifya.take.home.assignment.payment.order.service.domain.valueobject.PaymentOrderStatus;
import lombok.Getter;

import java.util.UUID;

@Getter
public class PaymentOrder extends AggregateRoot<PaymentOrderId> {
    private final CustomerId customerId;
    private final Money amount;
    private final UUID idempotentKey;

    private PaymentOrderStatus paymentOrderStatus;
    private String failureReason;

    public void initializePaymentOrder() {
        setId(new PaymentOrderId(UUID.randomUUID()));
        paymentOrderStatus = PaymentOrderStatus.PENDING;
    }

    public void validatePaymentOrder() throws PaymentOrderDomainException {
        if (amount == null || amount.isGreaterThanZero()) {
            throw new PaymentOrderDomainException("Amount must be greater than zero!");
        }
    }

    public void completePaymentOrder() throws PaymentOrderDomainException {
        if (paymentOrderStatus != PaymentOrderStatus.PENDING) {
            throw new PaymentOrderDomainException("Payment order is not in a correct state for complete payment operation!");
        }

        paymentOrderStatus = PaymentOrderStatus.COMPLETED;
    }

    public void cancelPaymentOrder(String failureReason) throws PaymentOrderDomainException {
        if (paymentOrderStatus != PaymentOrderStatus.PENDING) {
            throw new PaymentOrderDomainException("Payment order is not in a correct state for cancel payment operation!");
        }

        paymentOrderStatus = PaymentOrderStatus.CANCELED;
        this.failureReason = failureReason;
    }

    private PaymentOrder(Builder builder) {
        super.setId(builder.paymentOrderId);
        customerId = builder.customerId;
        amount = builder.amount;
        idempotentKey = builder.idempotentKey;
        paymentOrderStatus = builder.paymentOrderStatus;
        failureReason = builder.failureReason;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private PaymentOrderId paymentOrderId;
        private CustomerId customerId;
        private Money amount;
        private UUID idempotentKey;
        private PaymentOrderStatus paymentOrderStatus;
        private String failureReason;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder paymentOrderId(PaymentOrderId val) {
            paymentOrderId = val;
            return this;
        }

        public Builder customerId(CustomerId val) {
            customerId = val;
            return this;
        }

        public Builder amount(Money val) {
            amount = val;
            return this;
        }

        public Builder idempotentKey(UUID val) {
            idempotentKey = val;
            return this;
        }

        public Builder paymentOrderStatus(PaymentOrderStatus val) {
            paymentOrderStatus = val;
            return this;
        }

        public Builder failureMessage(String val) {
            failureReason = val;
            return this;
        }

        public PaymentOrder build() {
            return new PaymentOrder(this);
        }
    }
}
