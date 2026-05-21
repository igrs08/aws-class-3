package com.was.classe3.controller;

import com.was.classe3.model.Product;
import com.was.classe3.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody Product product) {

        service.save(product);

        return ResponseEntity.ok(
                "Product saved in DynamoDB"
        );
    }
}