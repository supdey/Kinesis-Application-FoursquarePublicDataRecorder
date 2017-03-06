/*
 * Developed by Alex Grace for research purposes only. (ag00248@surrey.ac.uk)
 */

package com.alexgrace.finalyearproject.kinesisclient.FoursquareEntities;

import java.util.List;

public class Entities {
    public List<Integer> getIndices() {
        return indices;
    }
    public String getType() {
        return type;
    }
    public String getId() {
        return id;
    }

    private List<Integer> indices;
    private String type;
    private String id;
}
