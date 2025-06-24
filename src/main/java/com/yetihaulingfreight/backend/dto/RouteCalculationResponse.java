package com.yetihaulingfreight.backend.dto;

public class RouteCalculationResponse {

    private String pickupAddress;
    private String deliveryAddress;

    private double distanceInMiles;
    private long estimatedTravelTimeInSeconds;
    private String formattedTravelTime;

    private double estimatedCost;

    private String routeSummary;

    private boolean success;
    private String message;

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public double getDistanceInMiles() {
        return distanceInMiles;
    }

    public void setDistanceInMiles(double distanceInMiles) {
        this.distanceInMiles = distanceInMiles;
    }

    public long getEstimatedTravelTimeInSeconds() {
        return estimatedTravelTimeInSeconds;
    }

    public void setEstimatedTravelTimeInSeconds(long estimatedTravelTimeInSeconds) {
        this.estimatedTravelTimeInSeconds = estimatedTravelTimeInSeconds;
    }

    public String getFormattedTravelTime() {
        return formattedTravelTime;
    }

    public void setFormattedTravelTime(String formattedTravelTime) {
        this.formattedTravelTime = formattedTravelTime;
    }

    public double getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public String getRouteSummary() {
        return routeSummary;
    }

    public void setRouteSummary(String routeSummary) {
        this.routeSummary = routeSummary;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
