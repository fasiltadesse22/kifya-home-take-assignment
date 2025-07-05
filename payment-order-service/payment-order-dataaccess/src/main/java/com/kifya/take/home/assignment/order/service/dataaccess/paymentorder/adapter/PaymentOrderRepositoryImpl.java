package com.kifya.take.home.assignment.order.service.dataaccess.paymentorder.adapter;

import com.kifya.take.home.assignment.order.service.dataaccess.paymentorder.entity.PaymentOrderEntity;
import com.kifya.take.home.assignment.order.service.dataaccess.paymentorder.mapper.PaymentOrderDataAccessMapper;
import com.kifya.take.home.assignment.order.service.dataaccess.paymentorder.repository.PaymentOrderJpaRepository;
import com.kifya.take.home.assignment.payment.order.service.domain.entity.PaymentOrder;
import com.kifya.take.home.assignment.payment.order.service.domain.ports.output.repository.PaymentOrderRepository;
import com.kifya.take.home.assignment.payment.order.service.domain.valueobject.PaymentOrderId;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class PaymentOrderRepositoryImpl implements PaymentOrderRepository {

    private final PaymentOrderJpaRepository paymentOrderJpaRepository;
    private final PaymentOrderDataAccessMapper paymentOrderDataAccessMapper;

    public PaymentOrderRepositoryImpl(PaymentOrderJpaRepository paymentOrderJpaRepository,
                                      PaymentOrderDataAccessMapper paymentOrderDataAccessMapper) {
        this.paymentOrderJpaRepository = paymentOrderJpaRepository;
        this.paymentOrderDataAccessMapper = paymentOrderDataAccessMapper;
    }

    @Override
    public PaymentOrder save(PaymentOrder paymentOrder) {
        PaymentOrderEntity paymentOrderEntity = paymentOrderJpaRepository.save(paymentOrderDataAccessMapper.paymentOrderToPaymentOrderEntity(paymentOrder));

        return paymentOrderDataAccessMapper.paymentOrderEntityToPaymentOrder(paymentOrderEntity);
    }

    @Override
    public Optional<PaymentOrder> findByPaymentOrderId(PaymentOrderId paymentOrderId) {
        return paymentOrderJpaRepository.findById(paymentOrderId.getValue())
                .map(paymentOrderDataAccessMapper::paymentOrderEntityToPaymentOrder);
    }

    @Override
    public Optional<PaymentOrder> findByIdempotentKey(UUID idempotentKey) {
        return paymentOrderJpaRepository.findByIdempotentKey(idempotentKey)
                .map(paymentOrderDataAccessMapper::paymentOrderEntityToPaymentOrder);
    }
}
