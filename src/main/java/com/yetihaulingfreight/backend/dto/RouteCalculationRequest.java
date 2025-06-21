package com.yetihaulingfreight.backend.dto;

import jakarta.validation.constraints.Pattern;

public class RouteCalculationRequest {
    private String captchaToken;

    @Pattern(regexp = "^\\d{5}(-\\d{4})?$", message = "Invalid pickup ZIP code")
    private String pickupZip;

    @Pattern(regexp = "^\\d{5}(-\\d{4})?$", message = "Invalid delivery ZIP code")
    private String deliveryZip;

    public String getPickupZip() {
        return pickupZip;
    }

    public void setPickupZip(String pickupZip) {
        this.pickupZip = pickupZip;
    }

    public String getDeliveryZip() {
        return deliveryZip;
    }

    public void setDeliveryZip(String deliveryZip) {
        this.deliveryZip = deliveryZip;
    }

    public String getCaptchaToken() {
        return captchaToken;
    }

    public void setCaptchaToken(String captchaToken) {
        this.captchaToken = captchaToken;
    }
}
