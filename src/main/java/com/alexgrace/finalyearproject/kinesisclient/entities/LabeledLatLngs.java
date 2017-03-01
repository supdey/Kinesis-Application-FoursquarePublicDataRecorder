/*
 * Developed by Alex Grace for research purposes only. (ag00248@surrey.ac.uk)
 */

package com.alexgrace.finalyearproject.kinesisclient.entities;

public class LabeledLatLngs {
    public String getLabel() {
        return label;
    }
    public long getLat() {
        return lat;
    }
    public long getLng() {
        return lng;
    }

    private String label;
    private long lat;
    private long lng;
}
