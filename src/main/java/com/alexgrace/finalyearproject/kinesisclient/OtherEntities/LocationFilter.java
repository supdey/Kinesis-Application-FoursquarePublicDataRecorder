/*
 * Developed by Alex Grace for research purposes only. (ag00248@surrey.ac.uk)
 */

package com.alexgrace.finalyearproject.kinesisclient.OtherEntities;

public class LocationFilter {
    public double getLat() {
        return Lat;
    }
    public double getLng() {
        return Lng;
    }
    public int getRadius() {
        return Radius;
    }

    private double Lat;
    private double Lng;
    private int Radius;
}
