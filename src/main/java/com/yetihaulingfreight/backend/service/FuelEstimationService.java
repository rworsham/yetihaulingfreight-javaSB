package com.yetihaulingfreight.backend.service;

import org.springframework.stereotype.Service;

@Service
public class FuelEstimationService {

    private static final double AVERAGE_MPG = 6.0;
    private static final double DEFAULT_DIESEL_PRICE = 4.25;

    public double estimateGallonsUsed(double distanceMiles) {
        return distanceMiles / AVERAGE_MPG;
    }

    public double estimateFuelCost(double distanceMiles, double dieselPricePerGallon) {
        double gallonsUsed = estimateGallonsUsed(distanceMiles);
        return gallonsUsed * dieselPricePerGallon;
    }

    public double getDefaultDieselPrice() {
        return DEFAULT_DIESEL_PRICE;
    }
}