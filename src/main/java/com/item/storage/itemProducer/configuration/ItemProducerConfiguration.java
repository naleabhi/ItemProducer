package com.item.storage.itemProducer.configuration;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ItemProducerConfiguration {

    KafkaConfiguration kafkaConfiguration;
    public ItemProducerConfiguration(KafkaConfiguration kafkaConfiguration)
    {
        this.kafkaConfiguration=kafkaConfiguration;
    }
    @Bean
    public ProducerFactory<Integer, String> producerFactory()
    {
       Map<String, Object> kafkaProperties= new HashMap<>();
       kafkaProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfiguration.getBootstrapServers());
       kafkaProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaConfiguration.getKeySerializer());
       kafkaProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaConfiguration.getValueSerializer());
       kafkaProperties.put(ProducerConfig.ACKS_CONFIG, kafkaConfiguration.getProperties().get("acks"));
       kafkaProperties.put(ProducerConfig.RETRIES_CONFIG, kafkaConfiguration.getProperties().get("retries"));
       kafkaProperties.put(ProducerConfig.RECEIVE_BUFFER_CONFIG, kafkaConfiguration.getProperties().get("retry-backoff-ms"));
       kafkaProperties.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, kafkaConfiguration.getProperties().get("delivery-timeout-ms"));
       kafkaProperties.put(ProducerConfig.LINGER_MS_CONFIG, 100);
       kafkaProperties.put(ProducerConfig.BATCH_SIZE_CONFIG, 3);

       return new DefaultKafkaProducerFactory<>(kafkaProperties);
    }
    @Bean
    public KafkaTemplate<Integer, String>itemProducerTemplate()
    {
            return new KafkaTemplate<>(producerFactory());
    }
}
