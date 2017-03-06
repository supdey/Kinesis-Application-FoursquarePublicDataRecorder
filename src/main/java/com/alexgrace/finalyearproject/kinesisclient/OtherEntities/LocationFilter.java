/*
 * Developed by Alex Grace for research purposes only. (ag00248@surrey.ac.uk)
 */

package com.alexgrace.finalyearproject.kinesisclient.OtherEntities;

public class LocationFilter {
    public double getLat() {
        return lat;
    }
    public double getLng() {
        return lng;
    }
    public int getRadius() {
        return radius;
    }

    private double lat;
    private double lng;
    private int radius;
}
