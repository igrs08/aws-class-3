package com.was.classe3.service;

import com.was.classe3.model.Product;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductService {

    private final DynamoDbClient dynamoDbClient;

    public ProductService(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    public void save(Product product) {

        Map<String, AttributeValue> item =
                new HashMap<>();

        item.put(
                "id",
                AttributeValue.builder()
                        .s(product.getId())
                        .build()
        );

        item.put(
                "name",
                AttributeValue.builder()
                        .s(product.getName())
                        .build()
        );

        item.put(
                "price",
                AttributeValue.builder()
                        .n(product.getPrice().toString())
                        .build()
        );

        PutItemRequest request =
                PutItemRequest.builder()
                        .tableName("products")
                        .item(item)
                        .build();

        dynamoDbClient.putItem(request);
    }
}