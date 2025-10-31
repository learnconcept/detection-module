package com.javaconcept.events;

public record Transaction(
        String transactionId,
        String userId,
        Double amount,
        String timeStamp) {
}
