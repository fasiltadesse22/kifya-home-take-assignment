package com.kifya.take.home.assignment.order.service.dataaccess.paymentorder.repository;

import com.kifya.take.home.assignment.order.service.dataaccess.paymentorder.entity.PaymentOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentOrderJpaRepository extends JpaRepository<PaymentOrderEntity, UUID> {

    Optional<PaymentOrderEntity> findById(UUID paymentOrderId);
    Optional<PaymentOrderEntity> findByIdempotentKey(UUID idempotentKey);
}
