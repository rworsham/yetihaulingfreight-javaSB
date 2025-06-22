package com.yetihaulingfreight.backend.service;

import com.yetihaulingfreight.backend.dto.EstimatedRoute;
import com.yetihaulingfreight.backend.dto.QuoteRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class QuoteService {

    private static final Logger log = LoggerFactory.getLogger(QuoteService.class);

    private final RecaptchaService recaptchaService;
    private final RouteCalculationService routeCalculationService;
    private final EmailService emailService;

    public QuoteService(
            RecaptchaService recaptchaService,
            RouteCalculationService routeCalculationService,
            EmailService emailService
    ) {
        this.recaptchaService = recaptchaService;
        this.routeCalculationService = routeCalculationService;
        this.emailService = emailService;
    }

    public void processQuote(QuoteRequest quoteRequest) {
        boolean captchaValid = recaptchaService.verifyCaptcha(quoteRequest.getCaptchaToken(), "quote_form_submit");

        if (!captchaValid) {
            throw new IllegalArgumentException("CAPTCHA validation failed");
        }

        EstimatedRoute estimatedRoute = null;
        try {
            estimatedRoute = routeCalculationService.estimateRoute(quoteRequest);
        } catch (Exception e) {
            log.warn("Failed to estimate route for quote request: {}", e.getMessage(), e);
        }

        emailService.sendQuoteEmail(quoteRequest, estimatedRoute);
    }
}