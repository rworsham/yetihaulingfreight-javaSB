package com.yetihaulingfreight.backend.dto;

public class GeocodingCoordinateResponse {
    private double lat;
    private double lng;

    public GeocodingCoordinateResponse() {}

    public GeocodingCoordinateResponse(double lat, double lng) {
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