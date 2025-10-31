package com.javaconcept.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {

    @Bean
    public NewTopic createTransTopic(){
        return new NewTopic("transaction_topic", 3, (short) 1);
    }

    @Bean
    public NewTopic createFraudTopic(){
        return new NewTopic("fraud_topic", 3, (short) 1);
    }
}
