package com.fcs.payment.adapter.entrypoint;

import com.fcs.payment.adapter.dataprovider.kafka.message.PaymentRequestMessage;
import com.fcs.payment.usecase.input.ProcessPaymentRequestInputBoundary;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentRequestedKafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentRequestedKafkaConsumer.class);

    private final ProcessPaymentRequestInputBoundary inputBoundary;

    @KafkaListener(topics = "${payment.requests.topic.name}", groupId = "${payment.requests.consumer.group.id}")
    public void receive(PaymentRequestMessage message) {
        LOGGER.info("Received Message in group paymentRequestsConsumerGroup: " + message);
        inputBoundary.execute(message.reservationId(), message.encryptedPaymentData());
    }
}
