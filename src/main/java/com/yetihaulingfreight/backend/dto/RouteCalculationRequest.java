package com.yetihaulingfreight.backend.dto;

public class RouteCalculationRequest {

    private String captchaToken;

    private String pickupAddress;

    private String pickupCounty;

    private String pickupState;

    private String pickupZip;

    private String deliveryAddress;

    private String deliveryCounty;

    private String deliveryState;

    private String deliveryZip;

    public String getCaptchaToken() {
        return captchaToken;
    }

    public void setCaptchaToken(String captchaToken) {
        this.captchaToken = captchaToken;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public String getPickupCounty() {
        return pickupCounty;
    }

    public void setPickupCounty(String pickupCounty) {
        this.pickupCounty = pickupCounty;
    }

    public String getPickupState() {
        return pickupState;
    }

    public void setPickupState(String pickupState) {
        this.pickupState = pickupState;
    }

    public String getPickupZip() {
        return pickupZip;
    }

    public void setPickupZip(String pickupZip) {
        this.pickupZip = pickupZip;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryCounty() {
        return deliveryCounty;
    }

    public void setDeliveryCounty(String deliveryCounty) {
        this.deliveryCounty = deliveryCounty;
    }

    public String getDeliveryState() {
        return deliveryState;
    }

    public void setDeliveryState(String deliveryState) {
        this.deliveryState = deliveryState;
    }

    public String getDeliveryZip() {
        return deliveryZip;
    }

    public void setDeliveryZip(String deliveryZip) {
        this.deliveryZip = deliveryZip;
    }

}
