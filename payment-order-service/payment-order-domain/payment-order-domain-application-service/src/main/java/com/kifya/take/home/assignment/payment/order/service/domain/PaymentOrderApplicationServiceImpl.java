package com.kifya.take.home.assignment.payment.order.service.domain;

import com.kifya.take.home.assignment.payment.order.service.domain.dto.create.CreatePaymentOrderCommand;
import com.kifya.take.home.assignment.payment.order.service.domain.dto.create.CreatePaymentOrderResponse;
import com.kifya.take.home.assignment.payment.order.service.domain.dto.track.TrackPaymentOrderQuery;
import com.kifya.take.home.assignment.payment.order.service.domain.dto.track.TrackPaymentOrderResponse;
import com.kifya.take.home.assignment.payment.order.service.domain.ports.input.service.PaymentOderApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
public class PaymentOrderApplicationServiceImpl implements PaymentOderApplicationService {

    private final PaymentOrderCreateCommandHandler paymentOrderCreateCommandHandler;
    private final PaymentOrderTrackCommandHandler paymentOrderTrackCommandHandler;

    public PaymentOrderApplicationServiceImpl(PaymentOrderCreateCommandHandler paymentOrderCreateCommandHandler,
                                              PaymentOrderTrackCommandHandler paymentOrderTrackCommandHandler) {
        this.paymentOrderCreateCommandHandler = paymentOrderCreateCommandHandler;
        this.paymentOrderTrackCommandHandler = paymentOrderTrackCommandHandler;
    }

    @Override
    public CreatePaymentOrderResponse createPaymentOrder(CreatePaymentOrderCommand createPaymentOrderCommand) {
        return paymentOrderCreateCommandHandler.createPaymentOrder(createPaymentOrderCommand);
    }

    @Override
    public TrackPaymentOrderResponse trackPaymentOrder(TrackPaymentOrderQuery trackPaymentOrderQuery) {
        return paymentOrderTrackCommandHandler.trackPaymentOrder(trackPaymentOrderQuery);
    }
}
