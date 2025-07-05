package com.kifya.take.home.assignment.order.service.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = { "com.kifya.take.home.assignment.order.service.dataaccess" })
@EntityScan(basePackages = { "com.kifya.take.home.assignment.order.service.dataaccess"})
@SpringBootApplication(scanBasePackages = "com.kifya.take.home.assignment")
public class OrderServiceApplication {
    public static void main(String[] args) {
      SpringApplication.run(OrderServiceApplication.class, args);
    }
}
