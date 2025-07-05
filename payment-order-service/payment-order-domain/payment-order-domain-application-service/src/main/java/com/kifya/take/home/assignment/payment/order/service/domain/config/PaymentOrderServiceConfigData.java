package com.kifya.take.home.assignment.payment.order.service.domain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "payment-order-service")
public class PaymentOrderServiceConfigData {
    private String paymentRequestTopicName;
    private String paymentResponseTopicName;
}
