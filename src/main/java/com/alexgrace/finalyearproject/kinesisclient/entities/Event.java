/*
 * Developed by Alex Grace for research purposes only. (ag00248@surrey.ac.uk)
 */

package com.alexgrace.finalyearproject.kinesisclient.entities;

import java.util.List;

public class Event {
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public List<Categories> getCategories() {
        return categories;
    }

    private String id;
    private String name;
    private List<Categories> categories;
}
