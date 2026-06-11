package com.fcs.payment.adapter.dataprovider.kafka.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        /* Configuracoes para timeout caso o Kafka nao esteja disponivel */
        configProps.put(ProducerConfig.RETRIES_CONFIG, 3); // Número de reenvios
        configProps.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 100); // Tempo de espera entre reenvios
        configProps.put(ProducerConfig.LINGER_MS_CONFIG, 5); // Tempo de espera antes de enviar lote
        configProps.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 4000); // Ajuste o timeout para 10 segundos
        configProps.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 4000); // Ajuste o tempo máximo de bloqueio para 10 segundos

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
