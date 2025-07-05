package com.food.ordering.system.order.service.application.rest;

import com.kifya.take.home.assignment.payment.order.service.domain.dto.create.CreatePaymentOrderCommand;
import com.kifya.take.home.assignment.payment.order.service.domain.dto.create.CreatePaymentOrderResponse;
import com.kifya.take.home.assignment.payment.order.service.domain.dto.track.TrackPaymentOrderQuery;
import com.kifya.take.home.assignment.payment.order.service.domain.dto.track.TrackPaymentOrderResponse;
import com.kifya.take.home.assignment.payment.order.service.domain.ports.input.service.PaymentOderApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/payment-orders", produces = "application/vnd.api.v1+json")
public class PaymentOrderController {

    private final PaymentOderApplicationService paymentOderApplicationService;

    public PaymentOrderController(PaymentOderApplicationService paymentOderApplicationService) {
        this.paymentOderApplicationService = paymentOderApplicationService;
    }

    @PostMapping
    public ResponseEntity<CreatePaymentOrderResponse> createOrder(@RequestBody CreatePaymentOrderCommand createPaymentOrderCommand) {
        log.info("Creating payment order for customer: {}", createPaymentOrderCommand.getCustomerId());
        CreatePaymentOrderResponse createPaymentOrderResponse = paymentOderApplicationService.createPaymentOrder(createPaymentOrderCommand);
        log.info("Payment order created with id: {}", createPaymentOrderResponse.getPaymentOrderId());
        return ResponseEntity.ok(createPaymentOrderResponse);
    }

    @GetMapping("/{paymentOrderId}")
    public ResponseEntity<TrackPaymentOrderResponse> getPaymentOrderByTrackingId(@PathVariable UUID paymentOrderId) {
       TrackPaymentOrderResponse trackPaymentOrderResponse =
               paymentOderApplicationService.trackPaymentOrder(TrackPaymentOrderQuery.builder().paymentOrderId(paymentOrderId).build());

       log.info("Returning payment order status with id: {}", trackPaymentOrderResponse.getPaymentOrderId());
       return  ResponseEntity.ok(trackPaymentOrderResponse);
    }
}
