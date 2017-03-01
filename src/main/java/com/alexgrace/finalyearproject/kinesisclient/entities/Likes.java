/*
 * Developed by Alex Grace for research purposes only. (ag00248@surrey.ac.uk)
 */

package com.alexgrace.finalyearproject.kinesisclient.entities;

import java.util.List;

public class Likes {
    public String getCount() {
        return count;
    }
    public List<Groups> getGroups() {
        return groups;
    }
    public String getSummary() {
        return summary;
    }

    private String count;
    private List<Groups> groups;
    private String summary;
}
