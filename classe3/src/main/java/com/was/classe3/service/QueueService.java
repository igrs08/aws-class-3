package com.was.classe3.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Service
public class QueueService {

    private final SqsClient sqsClient;

    private final String queueUrl =
            "https://sqs.sa-east-1.amazonaws.com/035268396982/cities";

    public QueueService(
            SqsClient sqsClient
    ) {
        this.sqsClient = sqsClient;
    }

    public void sendMessage(String city) {

        sqsClient.sendMessage(
                SendMessageRequest.builder()
                        .queueUrl(queueUrl)
                        .messageBody(city)
                        .build()
        );
    }
}