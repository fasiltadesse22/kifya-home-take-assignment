package com.kifya.take.home.assignment.payment.order.service.domain.ports.input.service;

import com.kifya.take.home.assignment.payment.order.service.domain.dto.create.CreatePaymentOrderCommand;
import com.kifya.take.home.assignment.payment.order.service.domain.dto.create.CreatePaymentOrderResponse;
import com.kifya.take.home.assignment.payment.order.service.domain.dto.track.TrackPaymentOrderQuery;
import com.kifya.take.home.assignment.payment.order.service.domain.dto.track.TrackPaymentOrderResponse;
import jakarta.validation.Valid;

public interface PaymentOderApplicationService {
    CreatePaymentOrderResponse createPaymentOrder(@Valid CreatePaymentOrderCommand createPaymentOrderCommand);
    TrackPaymentOrderResponse trackPaymentOrder(@Valid TrackPaymentOrderQuery trackPaymentOrderQuery);
}