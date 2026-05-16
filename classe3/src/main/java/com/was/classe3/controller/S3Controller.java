package com.was.classe3.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/s3")
public class S3Controller {

    private final com.was.classe3.service.S3Service service;

    public S3Controller(com.was.classe3.service.S3Service service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        service.upload(file);

        return ResponseEntity.ok("Upload realizado com sucesso");
    }

    @GetMapping("/files")
    public ResponseEntity<List<String>> listFiles() {
        return ResponseEntity.ok(service.listFiles());
    }
}
