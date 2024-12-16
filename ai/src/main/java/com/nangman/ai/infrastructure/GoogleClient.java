package com.nangman.ai.infrastructure;

import com.nangman.ai.application.service.GoogleService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
public class GoogleClient implements GoogleService {

    private final RestTemplate restTemplate;

    @Value("${service.google.api.key}")
    private String apiKey;

    private static final String URL = "https://generativelanguage.googleapis.com";

    public GoogleClient(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public String genAIResponse(String prompt) {
        URI uri = UriComponentsBuilder
                .fromUriString(URL)
                .path("/v1beta/models/gemini-1.5-flash-latest:generateContent")
                .queryParam("key", apiKey)
                .encode()
                .build()
                .toUri();
        log.info("uri = {}", uri);

        String requestBody = String.format("{ \"contents\": [{\"parts\": [{\"text\": \"%s\"}]}] }", prompt);

        RequestEntity<String> requestEntity = RequestEntity
                .post(uri)
                .header("Content-Type", "application/json")
                .body(requestBody);

        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
        log.info("API Status Code: {}", responseEntity.getStatusCode());
        return parseAIResponse(responseEntity.getBody());
    }

    private String parseAIResponse(String responseBody) {
        JSONObject jsonObject = new JSONObject(responseBody);
        return jsonObject
                .getJSONArray("candidates")
                .getJSONObject(0)
                .getJSONObject("content")
                .getJSONArray("parts")
                .getJSONObject(0)
                .getString("text");
    }
}
