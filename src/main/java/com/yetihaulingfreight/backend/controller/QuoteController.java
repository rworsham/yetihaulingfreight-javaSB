package com.yetihaulingfreight.backend.controller;

import com.yetihaulingfreight.backend.dto.QuoteRequest;
import com.yetihaulingfreight.backend.service.EmailService;
import com.yetihaulingfreight.backend.service.RecaptchaService;
import com.yetihaulingfreight.backend.service.RouteCalculationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/")
public class QuoteController {

    private static final Logger log = LoggerFactory.getLogger(QuoteController.class);
    private final EmailService emailService;
    private final RouteCalculationService routeCalculationService;
    private final RecaptchaService recaptchaService;

    public QuoteController(EmailService emailService, RouteCalculationService routeCalculationService, RecaptchaService recaptchaService) {
        this.emailService = emailService;
        this.routeCalculationService = routeCalculationService;
        this.recaptchaService = recaptchaService;
    }

    @PostMapping("/quote")
    public ResponseEntity<?> quote(
            @RequestBody QuoteRequest quoteRequest
    ) {
        try {
            boolean captchaValid = recaptchaService.verifyCaptcha(quoteRequest.getCaptchaToken(),"quote_form_submit");

            if (!captchaValid) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "CAPTCHA validation failed"));
            }

            EstimatedRoute estimatedRoute = null;
            try {
                estimatedRoute = routeCalculationService.estimateRoute(quoteRequest);
            } catch (Exception e) {
                log.warn("Failed to estimate route for quote request: {}", e.getMessage(), e);
            }

            emailService.sendQuoteEmail(quoteRequest, estimatedRoute);

            return ResponseEntity.ok(Map.of("message", "Quote message sent"));
        } catch (Exception e) {
            log.error("Failed to handle quote request: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Quote message failed"));
        }
    }
}
