/*
 * Developed by Alex Grace for research purposes only. (ag00248@surrey.ac.uk)
 */

package com.alexgrace.finalyearproject.kinesisclient.FoursquareEntities;

public class Delivery {
    public long getId() {
        return id;
    }
    public String getUrl() {
        return url;
    }
    public Provider getProvider() {
        return provider;
    }

    private long id;
    private String url;
    private Provider provider;
}
