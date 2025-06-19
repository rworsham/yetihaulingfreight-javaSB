package com.yetihaulingfreight.backend.dto;

import jakarta.validation.constraints.*;

public class QuoteRequest {

    @Pattern(regexp = "^\\d{5}(-\\d{4})?$", message = "Invalid pickup ZIP code")
    private String pickupZip;

    @Pattern(regexp = "^\\d{5}(-\\d{4})?$", message = "Invalid delivery ZIP code")
    private String deliveryZip;

    @NotNull
    @Min(1)
    private Integer weight;

    @NotNull
    @Min(1)
    private Integer length;

    @NotNull
    @Min(1)
    private Integer width;

    @NotBlank
    private String loadType;

    @Email
    private String email;

    @Pattern(regexp = "^(?:\\d[-]?){8}\\d$", message = "Must be 9 digits and may include hyphens")
    private String phoneNumber;

    private String captchaToken;

    public String getCaptchaToken() {
        return captchaToken;
    }

    public void setCaptchaToken(String captchaToken) {
        this.captchaToken = captchaToken;
    }

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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getLoadType() {
        return loadType;
    }

    public void setLoadType(String loadType) {
        this.loadType = loadType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
