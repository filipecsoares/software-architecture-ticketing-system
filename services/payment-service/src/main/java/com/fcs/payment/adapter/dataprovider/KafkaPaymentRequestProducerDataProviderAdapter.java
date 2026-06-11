package com.fcs.payment.adapter.dataprovider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fcs.payment.adapter.dataprovider.kafka.message.PaymentRequestMessage;
import com.fcs.payment.usecase.gateway.PaymentRequestGateway;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaPaymentRequestProducerDataProviderAdapter implements PaymentRequestGateway {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${payment.requests.topic.name}")
    private String paymentRequestsTopic;

    private final Logger LOGGER = LoggerFactory.getLogger(KafkaPaymentRequestProducerDataProviderAdapter.class);

    @Override
    public Boolean request(Integer reservationId, String encryptedPaymentData) {
        try {
            PaymentRequestMessage paymentRequestMessage = new PaymentRequestMessage(reservationId, encryptedPaymentData);
            String messageAsJson = objectMapper.writeValueAsString(paymentRequestMessage);
            kafkaTemplate.send(paymentRequestsTopic, messageAsJson).get();
        } catch (Exception e) {
            LOGGER.error("Falha ao converter ou postar mensagem para requisitar pagamento da reserva: {}", reservationId, e);
            return false;
        }
        return true;
    }
}
