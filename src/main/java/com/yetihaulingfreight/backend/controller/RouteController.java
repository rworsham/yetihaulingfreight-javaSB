package com.yetihaulingfreight.backend.controller;

import com.yetihaulingfreight.backend.dto.EstimatedRoute;
import com.yetihaulingfreight.backend.dto.RouteCalculationRequest;
import com.yetihaulingfreight.backend.dto.RouteEstimationRequest;
import com.yetihaulingfreight.backend.dto.RouteCalculationResponse;
import com.yetihaulingfreight.backend.service.RecaptchaService;
import com.yetihaulingfreight.backend.service.RouteCalculationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class RouteController {

    private static final Logger log = LoggerFactory.getLogger(RouteController.class);
    private final RouteCalculationService routeCalculationService;
    private final RecaptchaService recaptchaService;

    public RouteController(RouteCalculationService routeCalculationService, RecaptchaService recaptchaService) {
        this.routeCalculationService = routeCalculationService;
        this.recaptchaService = recaptchaService;
    }

    @PostMapping("/route/estimate")
    public ResponseEntity<?> estimateRoute(@RequestBody RouteEstimationRequest routeEstimationRequest) {
        try {
            boolean captchaValid = recaptchaService.verifyCaptcha(
                    routeEstimationRequest.getCaptchaToken(), "route_estimate_form_submit"
            );

            if (!captchaValid) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "CAPTCHA validation failed"));
            }

            EstimatedRoute route = routeCalculationService.estimateRoute(routeEstimationRequest);
            return ResponseEntity.ok(route);
        } catch (Exception e) {
            log.error("Route estimation failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Route generation failed"));
        }
    }

    @PostMapping("/route/calculation")
    public ResponseEntity<?> calculateRoute(@RequestBody RouteCalculationRequest routeCalculationRequest) {
        try {
            boolean captchaValid = recaptchaService.verifyCaptcha(
                    routeCalculationRequest.getCaptchaToken(), "route_form_submit"
            );

            if (!captchaValid) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "CAPTCHA validation failed"));
            }

            RouteCalculationResponse route = routeCalculationService.calculateRoute(routeCalculationRequest);
            return ResponseEntity.ok(route);
        } catch (Exception e) {
            log.error("Route calculation failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Route generation failed"));
        }
    }
}