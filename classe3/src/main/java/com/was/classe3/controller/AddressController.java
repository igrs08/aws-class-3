package com.was.classe3.controller;

import com.was.classe3.service.QueueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final QueueService queueService;

    public AddressController(
            QueueService queueService
    ) {
        this.queueService = queueService;
    }

    @PostMapping
    public ResponseEntity<String> process(

            @RequestParam String city

    ) {

        queueService.sendMessage(city);

        return ResponseEntity.ok(
                "Message sent to SQS"
        );
    }
}