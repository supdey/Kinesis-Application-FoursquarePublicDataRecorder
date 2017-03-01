/*
 * Developed by Alex Grace for research purposes only. (ag00248@surrey.ac.uk)
 */

package com.alexgrace.finalyearproject.kinesisclient.entities;

import java.util.List;

public class Image {
    public String getPrefix() {
        return prefix;
    }
    public List<Integer> getSizes() {
        return sizes;
    }
    public String getName() {
        return name;
    }

    private String prefix;
    private List<Integer> sizes;
    private String name;
}
