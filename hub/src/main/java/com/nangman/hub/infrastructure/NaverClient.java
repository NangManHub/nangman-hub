package com.nangman.hub.infrastructure;

import com.nangman.hub.application.dto.request.NaverRouteRequest;
import com.nangman.hub.application.dto.response.NaverRouteResponse;
import com.nangman.hub.application.service.NaverService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@Service
public class NaverClient implements NaverService {

    private final RestTemplate restTemplate;

    @Value("${service.naver.client.id}")
    private String clientId;
    @Value("${service.naver.client.secret}")
    private String clientSecret;

    private static final String ROUTE_API = "https://naveropenapi.apigw.ntruss.com";

    public NaverClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public NaverRouteResponse directions(NaverRouteRequest reqFrom, NaverRouteRequest reqTo) {
        URI uri = UriComponentsBuilder
                .fromUriString(ROUTE_API)
                .path("/map-direction/v1/driving")
                .queryParam("start", reqFrom.longitude() + "," + reqFrom.latitude())
                .queryParam("goal", reqTo.longitude() + "," + reqTo.latitude())
                .encode()
                .build()
                .toUri();
        log.info("uri = {}", uri);

        HttpHeaders headers = new HttpHeaders();
        headers.add("x-ncp-apigw-api-key-id", clientId);
        headers.add("x-ncp-apigw-api-key", clientSecret);

        RequestEntity<Void> requestEntity = RequestEntity
                .get(uri)
                .headers(headers)
                .build();

        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
        log.info("API Status Code: {}", responseEntity.getStatusCode());

        return fromJSONtoRouteDto(responseEntity.getBody());
    }

    public List<NaverRouteResponse> getBiDirections(NaverRouteRequest reqFrom, NaverRouteRequest reqTo) {
        return List.of(directions(reqFrom, reqTo), directions(reqTo, reqFrom));
    }

    private NaverRouteResponse fromJSONtoRouteDto(String responseBody) {
        JSONObject jsonObject = new JSONObject(responseBody);
        JSONObject routeRes = jsonObject
                .getJSONObject("route")
                .getJSONArray("traoptimal")
                .getJSONObject(0)
                .getJSONObject("summary");
        return NaverRouteResponse.from(routeRes);
    }
}
