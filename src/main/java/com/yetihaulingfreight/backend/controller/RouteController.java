package com.yetihaulingfreight.backend.controller;

import com.yetihaulingfreight.backend.dto.RouteCalculationRequest;
import com.yetihaulingfreight.backend.dto.RouteCalculationResponse;
import com.yetihaulingfreight.backend.service.RecaptchaService;
import com.yetihaulingfreight.backend.service.RouteCalculationService;
import com.yetihaulingfreight.backend.service.ZipCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.PublicKey;
import java.util.Map;

@RestController
@RequestMapping("/api/")
public class RouteController {

    private static final Logger log = LoggerFactory.getLogger(RouteController.class);
    private final ZipCodeService zipCodeService;
    private final RouteCalculationService routeCalculationService;
    private final RecaptchaService recaptchaService;

    public RouteController(ZipCodeService zipCodeService, RouteCalculationService routeCalculationService, RecaptchaService recaptchaService) {
        this.zipCodeService = zipCodeService;
        this.routeCalculationService = routeCalculationService;
        this.recaptchaService = recaptchaService;
    }

    @PostMapping("/route")
    public ResponseEntity<?> route(@RequestBody RouteCalculationRequest routeCalculationRequest) {
        try {
            boolean captchaValid = recaptchaService.verifyCaptcha(
                    routeCalculationRequest.getCaptchaToken(), "contact_form_submit"
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