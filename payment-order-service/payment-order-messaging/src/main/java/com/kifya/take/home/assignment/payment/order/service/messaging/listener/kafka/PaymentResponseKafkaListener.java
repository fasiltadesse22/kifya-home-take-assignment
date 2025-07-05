package com.kifya.take.home.assignment.payment.order.service.messaging.listener.kafka;

import com.kifya.take.home.assignment.kafka.consumer.KafkaConsumer;
import com.kifya.take.home.assignment.kafka.order.avro.model.PaymentResponseAvroModel;
import com.kifya.take.home.assignment.kafka.order.avro.model.PaymentStatus;
import com.kifya.take.home.assignment.payment.order.service.domain.dto.message.PaymentResponse;
import com.kifya.take.home.assignment.payment.order.service.domain.ports.input.message.listener.PaymentResponseMessageListener;
import com.kifya.take.home.assignment.payment.order.service.messaging.mapper.PaymentOrderMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class PaymentResponseKafkaListener implements KafkaConsumer<PaymentResponseAvroModel> {

    private final PaymentResponseMessageListener paymentResponseMessageListener;
    private final PaymentOrderMessagingDataMapper paymentOrderMessagingDataMapper;

    public PaymentResponseKafkaListener(PaymentResponseMessageListener paymentResponseMessageListener,
                                        PaymentOrderMessagingDataMapper paymentOrderMessagingDataMapper) {
        this.paymentResponseMessageListener = paymentResponseMessageListener;
        this.paymentOrderMessagingDataMapper = paymentOrderMessagingDataMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.payment-consumer-group-id}", topics = "${order-service.payment-response-topic-name}")
    public void receive(@Payload List<PaymentResponseAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.REPLY_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        log.info("{} number of payment responses received with keys: {} partitions: {} and offsets: {}",
                messages.size(), keys.toString(), partitions.toString(), offsets.toString());

        messages.forEach(paymentResponseAvroModel -> {
            if (PaymentStatus.COMPLETED == paymentResponseAvroModel.getPaymentStatus()) {
                log.info("Processing successful payment for order id: {}", paymentResponseAvroModel.getPaymentOrderId());
                PaymentResponse paymentResponse = paymentOrderMessagingDataMapper.paymentResponseAvroModelToPaymentResponse(paymentResponseAvroModel);
                paymentResponseMessageListener.paymentOrderCompleted(paymentResponse);
            } else if (PaymentStatus.CANCELLED == paymentResponseAvroModel.getPaymentStatus()
                    || PaymentStatus.FAILED == paymentResponseAvroModel.getPaymentStatus()) {
                log.info("Processing unsuccessful payment for order id: {}", paymentResponseAvroModel.getPaymentOrderId());
                paymentResponseMessageListener.paymentOrderCanceled(paymentOrderMessagingDataMapper
                        .paymentResponseAvroModelToPaymentResponse(paymentResponseAvroModel));
            }
        });
    }
}
