package com.yetihaulingfreight.backend.dto;

public class EstimatedRoute {
    private double distanceMiles;
    private long durationSeconds;
    private String formattedDuration;
    private double estimatedFuelUsedGallons;
    private double estimatedFuelCost;

    public EstimatedRoute(double distanceMiles, long durationSeconds) {
        this.distanceMiles = distanceMiles;
        this.durationSeconds = durationSeconds;
        this.formattedDuration = formatDuration(durationSeconds);
    }

    public EstimatedRoute(double distanceMiles, long durationSeconds, double estimatedFuelUsedGallons, double estimatedFuelCost) {
        this.distanceMiles = distanceMiles;
        this.durationSeconds = durationSeconds;
        this.formattedDuration = formatDuration(durationSeconds);
        this.estimatedFuelUsedGallons = estimatedFuelUsedGallons;
        this.estimatedFuelCost = estimatedFuelCost;
    }

    private String formatDuration(long durationSeconds) {
        long hours = durationSeconds / 3600;
        long minutes = (durationSeconds % 3600) / 60;

        if (hours > 0) {
            return String.format("%d hr %d min", hours, minutes);
        } else {
            return String.format("%d min", minutes);
        }
    }

    public double getDistanceMiles() {
        return distanceMiles;
    }

    public void setDistanceMiles(double distanceMiles) {
        this.distanceMiles = distanceMiles;
    }

    public long getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(long durationSeconds) {
        this.durationSeconds = durationSeconds;
        this.formattedDuration = formatDuration(durationSeconds);
    }

    public String getFormattedDuration() {
        return formattedDuration;
    }

    public double getEstimatedFuelUsedGallons() {
        return estimatedFuelUsedGallons;
    }

    public void setEstimatedFuelUsedGallons(double estimatedFuelUsedGallons) {
        this.estimatedFuelUsedGallons = estimatedFuelUsedGallons;
    }

    public double getEstimatedFuelCost() {
        return estimatedFuelCost;
    }

    public void setEstimatedFuelCost(double estimatedFuelCost) {
        this.estimatedFuelCost = estimatedFuelCost;
    }
}
