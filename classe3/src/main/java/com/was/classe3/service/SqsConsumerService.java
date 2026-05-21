package com.was.classe3.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class SqsConsumerService {

    private final SqsClient sqsClient;

    private final DynamoDbClient dynamoDbClient;

    private final WebClient webClient =  WebClient.builder().build();

    private final String queueUrl =
            "https://sqs.sa-east-1.amazonaws.com/035268396982/cities";

    public SqsConsumerService(
            SqsClient sqsClient,
            DynamoDbClient dynamoDbClient
    ) {

        this.sqsClient = sqsClient;
        this.dynamoDbClient = dynamoDbClient;
    }

    @Scheduled(fixedDelay = 5000)
    public void consumeMessages() {

        ReceiveMessageRequest request =
                ReceiveMessageRequest.builder()
                        .queueUrl(queueUrl)
                        .maxNumberOfMessages(10)
                        .build();

        List<Message> messages = sqsClient.receiveMessage(request).messages();

        for (Message message : messages) {

            processMessage(message);

            sqsClient.deleteMessage(DeleteMessageRequest.builder()
                            .queueUrl(queueUrl)
                            .receiptHandle(message.receiptHandle())
                            .build()
            );
        }
    }

    private void processMessage(Message message) {

        String city = message.body();

        long startTime =
                System.currentTimeMillis();

        webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("nominatim.openstreetmap.org")
                        .path("/search")
                        .queryParam("city", city)
                        .queryParam("format", "json")
                        .queryParam("addressdetails", 1)
                        .build()
                )
                .header(
                        "User-Agent",
                        "MeuApp/1.0"
                )
                .retrieve()
                .bodyToMono(String.class)
                .block();

        long endTime =
                System.currentTimeMillis();

        saveMetrics(
                city,
                startTime,
                endTime
        );
    }

    private void saveMetrics(
            String city,
            long startTime,
            long endTime
    ) {

        Map<String, AttributeValue> item = new HashMap<>();

        item.put(
                "id",
                AttributeValue.builder()
                        .s(UUID.randomUUID().toString())
                        .build()
        );

        item.put(
                "city",
                AttributeValue.builder()
                        .s(city)
                        .build()
        );

        item.put(
                "startTime",
                AttributeValue.builder()
                        .n(String.valueOf(startTime))
                        .build()
        );

        item.put(
                "endTime",
                AttributeValue.builder()
                        .n(String.valueOf(endTime))
                        .build()
        );

        item.put(
                "durationMs",
                AttributeValue.builder()
                        .n(String.valueOf(
                                endTime - startTime
                        ))
                        .build()
        );

        dynamoDbClient.putItem(
                PutItemRequest.builder()
                        .tableName("request_metrics")
                        .item(item)
                        .build()
        );
    }
}