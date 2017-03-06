/*
 * Developed by Alex Grace for research purposes only. (ag00248@surrey.ac.uk)
 */

package com.alexgrace.finalyearproject.kinesisclient.FoursquareEntities;

import java.util.List;

public class Location {
    public String getAddress() {
        return address;
    }
    public String getCrossStreet() {
        return crossStreet;
    }
    public double getLat() {
        return lat;
    }
    public double getLng() {
        return lng;
    }
    public boolean getIsFuzzed() {
        return isFuzzed;
    }
    public List<LabeledLatLngs> getLabeledLatLngs() {
        return labeledLatLngs;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public String getCc() {
        return cc;
    }
    public String getNeighborhood() {
        return neighborhood;
    }
    public String getCity() {
        return city;
    }
    public String getState() {
        return state;
    }
    public String getCountry() {
        return country;
    }
    public List<String> getFormattedAddress() {
        return formattedAddress;
    }

    private String address;
    private String crossStreet;
    private double lat;
    private double lng;
    private boolean isFuzzed;
    private List<LabeledLatLngs> labeledLatLngs;
    private String postalCode;
    private String cc;
    private String neighborhood;
    private String city;
    private String state;
    private String country;
    private List<String> formattedAddress;
}
