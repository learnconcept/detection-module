package com.javaconcept.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaconcept.events.Transaction;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

@RestController
@RequestMapping("/api/transactions")
public class ProducerController {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    public ProducerController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public String sendTransction() throws JsonProcessingException {
        for(int i=0; i<10; i++){
            String tran_id = "tran-"+System.currentTimeMillis()+" "+i;
            Double amount = 10000 + new Random().nextDouble() * (14000-10000);
            Transaction transaction = new Transaction(tran_id, "USER_"+i, amount,
                    LocalDateTime.now().toString());

            kafkaTemplate.send("transaction_topic", tran_id,
                    mapper.writeValueAsString(transaction));
        }
        return "Successfully sent the transaction";
    }





}
