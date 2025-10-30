package com.kls.references.bank.branches.directory.api.utils;

public class DistanceCalculator {

    private DistanceCalculator() {}

    public static Double getDistanceBetween(
        Double originX,
        Double originY,
        Double destinationX,
        Double destinationY
    ) {
        Double deltaX = destinationX - originX;
        Double deltaY = destinationY - originY;

        return Math.hypot(deltaX, deltaY);
    }

}
