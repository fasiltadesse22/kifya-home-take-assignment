package com.kifya.take.home.assignment.order.service.domain;

import com.kifya.take.home.assignment.payment.order.service.domain.PaymentOrderDomainService;
import com.kifya.take.home.assignment.payment.order.service.domain.PaymentOrderDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public PaymentOrderDomainService paymentOrderDomainService() {
        return new PaymentOrderDomainServiceImpl();
    }
}
