/*
 * Developed by Alex Grace for research purposes only. (ag00248@surrey.ac.uk)
 */

package com.alexgrace.finalyearproject.kinesisclient.entities;

import java.util.List;

public class Groups {
    public String getType() {
        return type;
    }
    public int getCount() {
        return count;
    }
    public List<User> getItems() {
        return items;
    }

    private String type;
    private int count;
    private List<User> items;
}
