package com.yetihaulingfreight.backend.dto;

public class EstimatedRoute {
    private double distanceMiles;
    private long durationSeconds;
    private String formattedDuration;

    public EstimatedRoute(double distanceMiles, long durationSeconds) {
        this.distanceMiles = distanceMiles;
        this.durationSeconds = durationSeconds;
        this.formattedDuration = formatDuration(durationSeconds);
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
}