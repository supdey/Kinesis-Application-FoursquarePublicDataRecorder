/*
 * Developed by Alex Grace for research purposes only. (ag00248@surrey.ac.uk)
 */

package com.alexgrace.finalyearproject.kinesisclient.FoursquareEntities;

import java.util.List;

public class Events {
    public int getCount() {
        return count;
    }
    public String getSummary() {
        return summary;
    }
    public List<EventItem> getItems() {
        return items;
    }

    private int count;
    private String summary;
    private List<EventItem> items;
}
