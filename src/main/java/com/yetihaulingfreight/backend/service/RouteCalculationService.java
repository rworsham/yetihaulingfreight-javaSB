package com.yetihaulingfreight.backend.service;

import com.yetihaulingfreight.backend.dto.EstimatedRoute;
import com.yetihaulingfreight.backend.dto.QuoteRequest;
import com.yetihaulingfreight.backend.dto.RouteCalculationResponse;
import com.yetihaulingfreight.backend.dto.ZipCodeCoordinateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class RouteCalculationService {

    private final ZipCodeService zipCodeService;
    private final RestTemplate restTemplate;

    @Value("${HERE_API_KEY}")
    private String hereApiKey;

    public RouteCalculationService(ZipCodeService zipCodeService, RestTemplate restTemplate) {
        this.zipCodeService = zipCodeService;
        this.restTemplate = restTemplate;
    }

    public EstimatedRoute estimateRoute(QuoteRequest quoteRequest) {
        ZipCodeCoordinateResponse origin = zipCodeService.getCoordinatesByZip(quoteRequest.getPickupZip());
        ZipCodeCoordinateResponse destination = zipCodeService.getCoordinatesByZip(quoteRequest.getDeliveryZip());

        String url = String.format(
                "https://router.hereapi.com/v8/routes?transportMode=truck&origin=%f,%f&destination=%f,%f&return=summary&apikey=%s",
                origin.getLat(), origin.getLng(),
                destination.getLat(), destination.getLng(),
                hereApiKey
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            Map<String, Object> route = ((List<Map<String, Object>>) response.getBody().get("routes")).get(0);
            Map<String, Object> section = ((List<Map<String, Object>>) route.get("sections")).get(0);
            Map<String, Object> summary = (Map<String, Object>) section.get("summary");

            double distanceMeters = ((Number) summary.get("length")).doubleValue();
            long durationSeconds = ((Number) summary.get("duration")).longValue();

            double distanceMiles = distanceMeters / 1609.34;

            return new EstimatedRoute(distanceMiles, durationSeconds);
        }

        throw new RuntimeException("Failed to calculate route from HERE API");
    }
}
