package com.blueyonder.test.sumit.restkafkademo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.JsonMessageConverter;

/**
 * Helps in de marshalling string into required object in {@link org.springframework.kafka.annotation.KafkaListener}
 */
@Configuration
public class ConverterConfig {

    @Bean
    public JsonMessageConverter jsonMessageConverter() {
        return new JsonMessageConverter();
    }
}
