package com.javaconcept.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaconcept.events.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@Configuration
@EnableKafkaStreams
@Slf4j
public class FraudDetectionStreams {

    private final ObjectMapper mapper = new ObjectMapper();

    @Bean
    public KStream<String, String> fraudDetectStream(StreamsBuilder builder) throws RuntimeException{
        KStream<String, String> tranStream = builder.stream("transaction_topic",
                Consumed.with(Serdes.String(), Serdes.String()));
        KStream<String, String> fraudTranStream = tranStream
                .filter((k, v)-> {
                    try {
                        return mapper.readValue(v, Transaction.class)
                                .amount() > 10000;
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }).peek((k, v)->{
                    log.warn("Alert TransationId={}, value={}", k, v);
                });

        fraudTranStream.to("fraud_topic");
        return tranStream;
    }
}
