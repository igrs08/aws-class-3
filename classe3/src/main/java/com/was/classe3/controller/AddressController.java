package com.was.classe3.controller;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AddressController {

    private final WebClient webClient =
            WebClient.builder().build();

    @GetMapping("/address")
    public Mono<Map<String, String>> searchAddress(

            @RequestParam String city

    ) {

        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("nominatim.openstreetmap.org")
                        .path("/search")
                        //.queryParam("street", street)
                        .queryParam("city", city)
                        //.queryParam("state", state)
                        .queryParam("format", "json")
                        .queryParam("addressdetails", 1)
                        .build()
                )
                .header(
                        "User-Agent",
                        "MeuAppConsultas/1.0 (contato@seu-email.com)"
                )
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {})
                .map(response -> {

                    if (response.isEmpty()) {
                        throw new RuntimeException("Address not found");
                    }

                    Map<String, Object> firstResult = response.get(0);

                    Map<String, String> result = new HashMap<>();

                    result.put("latitude", firstResult.get("lat").toString());
                    result.put("longitude", firstResult.get("lon").toString());

                    return result;
                });
    }
}