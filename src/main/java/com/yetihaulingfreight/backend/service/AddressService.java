package com.yetihaulingfreight.backend.service;

import com.yetihaulingfreight.backend.dto.GeocodingCoordinateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
public class AddressService {

    private final RestTemplate restTemplate;

    @Value("${HERE_API_KEY}")
    private String hereApiKey;

    @Autowired
    public AddressService(RestTemplate restTemplate) {this.restTemplate = restTemplate;}

    public GeocodingCoordinateResponse getCoordinatesByAddress(String address) {
        String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
        String url = "https://geocode.search.hereapi.com/v1/geocode?q=" + encodedAddress + "&apiKey=" + hereApiKey;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                Map.class
        );

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            List<Map<String, Object>> items = (List<Map<String, Object>>) response.getBody().get("items");

            if (items != null && !items.isEmpty()) {
                Map<String, Object> position = (Map<String, Object>) items.get(0).get("position");
                double lat = ((Number) position.get("lat")).doubleValue();
                double lng = ((Number) position.get("lng")).doubleValue();
                return new GeocodingCoordinateResponse(lat, lng);
            } else {
                throw new RuntimeException("No geocoding results found for address: " + address);
            }
        } else {
            throw new RuntimeException("Failed to fetch coordinates for address");
        }
    }
}