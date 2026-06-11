package com.fcs.payment.adapter.dataprovider.kafka.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fcs.payment.adapter.dataprovider.kafka.message.PaymentRequestMessage;
import org.apache.kafka.common.serialization.Deserializer;

public class PaymentRequestDeserializer implements Deserializer<PaymentRequestMessage> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public PaymentRequestMessage deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, PaymentRequestMessage.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize PaymentRequestMessage", e);
        }
    }
}
