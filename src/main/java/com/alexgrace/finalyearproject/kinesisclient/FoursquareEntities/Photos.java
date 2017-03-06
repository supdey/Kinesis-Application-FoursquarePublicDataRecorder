/*
 * Developed by Alex Grace for research purposes only. (ag00248@surrey.ac.uk)
 */

package com.alexgrace.finalyearproject.kinesisclient.FoursquareEntities;

import java.util.List;

public class Photos {
    public int getCount() {
        return count;
    }
    public List<Items> getItems() {
        return items;
    }
    public Layout getLayout() {
        return layout;
    }

    private int count;
    private List<Items> items;
    private Layout layout;
}
