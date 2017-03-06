/*
 * Developed by Alex Grace for research purposes only. (ag00248@surrey.ac.uk)
 */

package com.alexgrace.finalyearproject.kinesisclient.FoursquareEntities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Photo {
    public String getPrefix() {
        return prefix;
    }
    public String getSuffix() {
        return suffix;
    }
    public boolean getDefault() {
        return fsqdefault;
    }

    private String prefix;
    private String suffix;
    @JsonProperty(value = "default")
    private boolean fsqdefault;
}
