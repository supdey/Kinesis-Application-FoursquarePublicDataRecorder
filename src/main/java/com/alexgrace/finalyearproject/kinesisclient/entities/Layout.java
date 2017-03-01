/*
 * Developed by Alex Grace for research purposes only. (ag00248@surrey.ac.uk)
 */

package com.alexgrace.finalyearproject.kinesisclient.entities;

public class Layout {
    public String getName() {
        return name;
    }
    public LayoutDetails getTop() {
        return top;
    }
    public LayoutDetails getBottomLeft() {
        return bottomLeft;
    }
    public LayoutDetails getBottomRight() {
        return bottomRight;
    }

    private String name;
    private LayoutDetails top;
    private LayoutDetails bottomLeft;
    private LayoutDetails bottomRight;
}
