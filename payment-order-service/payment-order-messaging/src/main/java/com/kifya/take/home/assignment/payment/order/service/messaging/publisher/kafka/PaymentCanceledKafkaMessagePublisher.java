package com.kifya.take.home.assignment.payment.order.service.messaging.publisher.kafka;

import com.kifya.home.take.assignment.kafka.producer.service.KafkaProducer;
import com.kifya.take.home.assignment.kafka.order.avro.model.PaymentRequestAvroModel;
import com.kifya.take.home.assignment.payment.order.service.domain.config.PaymentOrderServiceConfigData;
import com.kifya.take.home.assignment.payment.order.service.domain.event.PaymentOrderCanceledEvent;
import com.kifya.take.home.assignment.payment.order.service.domain.ports.output.message.publisher.PaymentOrderCanceledRequestMessagePublisher;
import com.kifya.take.home.assignment.payment.order.service.messaging.mapper.PaymentOrderMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Slf4j
@Component
public class PaymentCanceledKafkaMessagePublisher implements PaymentOrderCanceledRequestMessagePublisher {

    private final PaymentOrderMessagingDataMapper paymentOrderMessagingDataMapper;
    private final PaymentOrderServiceConfigData paymentOrderServiceConfigData;
    private final KafkaProducer<String, PaymentRequestAvroModel> kafakaProducer;

    public PaymentCanceledKafkaMessagePublisher(PaymentOrderMessagingDataMapper paymentOrderMessagingDataMapper,
                                                PaymentOrderServiceConfigData paymentOrderServiceConfigData,
                                                KafkaProducer<String, PaymentRequestAvroModel> kafakaProducer) {
        this.paymentOrderMessagingDataMapper = paymentOrderMessagingDataMapper;
        this.paymentOrderServiceConfigData = paymentOrderServiceConfigData;
        this.kafakaProducer = kafakaProducer;
    }

    @Override
    public void publish(PaymentOrderCanceledEvent paymentOrderCanceledEvent) {
        String orderId = paymentOrderCanceledEvent.getPaymentOrder().getId().getValue().toString();
        log.info("Received PaymentOrderCanceledEvent for orderId: {}", orderId);

        try {
            PaymentRequestAvroModel paymentRequestAvroModel
                    = paymentOrderMessagingDataMapper.orderCanceledEventToPaymentRequestAvroModel(paymentOrderCanceledEvent);

            kafakaProducer.send(paymentOrderServiceConfigData.getPaymentRequestTopicName(),
                    orderId,
                    paymentRequestAvroModel,
                    getKafkaCallback(paymentOrderServiceConfigData.getPaymentRequestTopicName(), paymentRequestAvroModel));

            log.info("PaymentRequestAvroModel sent to kafka for orderId: {}", paymentRequestAvroModel.getPaymentOrderId());
        } catch (Exception e) {
            log.error("Error while sending PaymenrRequestAvroModel message to kafka with " +
                    "order id: {}, error: {}", orderId, e.getMessage());
        }
    }

    private BiConsumer<SendResult<String, PaymentRequestAvroModel>, Throwable>
    getKafkaCallback(String paymentRequestTopicName, PaymentRequestAvroModel paymentRequestAvroModel) {
        return (result, throwable) -> {
            if (throwable != null) {
                log.error("Error sending PaymentRequestAvroModel message {} to topic {}",
                        paymentRequestAvroModel.toString(), paymentRequestTopicName, throwable);
            } else {
                RecordMetadata metadata = result.getRecordMetadata();

                log.info("Recieved successful response from kafka for order id: {} " +
                                "Topic: {} Partition: {} Offset: {} timestamp:: {}",
                        paymentRequestAvroModel.getPaymentOrderId(),
                        metadata.topic(),
                        metadata.partition(),
                        metadata.offset(),
                        metadata.timestamp());
            }
        };
    }
}
