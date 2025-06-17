package com.yetihaulingfreight.backend.dto;

public class ZipCodeCoordinateResponse {
    private double lat;
    private double lng;

    public ZipCodeCoordinateResponse() {}

    public ZipCodeCoordinateResponse(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    @Override
    public String toString() {
        return lat + "," + lng;
    }
}