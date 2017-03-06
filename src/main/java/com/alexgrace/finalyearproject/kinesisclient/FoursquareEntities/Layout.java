/*
 * Developed by Alex Grace for research purposes only. (ag00248@surrey.ac.uk)
 */

package com.alexgrace.finalyearproject.kinesisclient.FoursquareEntities;

public class Layout {
    public String getName() {
        return name;
    }
    public LayoutDetails getTop() {
        return top;
    }
    public LayoutDetails getLeft() {
        return left;
    }
    public LayoutDetails getRight() {
        return right;
    }
    public LayoutDetails getBottom() {
        return bottom;
    }
    public LayoutDetails getTopLeft() {
        return topLeft;
    }
    public LayoutDetails getTopRight() {
        return topRight;
    }
    public LayoutDetails getBottomLeft() {
        return bottomLeft;
    }
    public LayoutDetails getBottomRight() {
        return bottomRight;
    }

    private String name;
    private LayoutDetails top;
    private LayoutDetails left;
    private LayoutDetails right;
    private LayoutDetails bottom;
    private LayoutDetails topLeft;
    private LayoutDetails topRight;
    private LayoutDetails bottomLeft;
    private LayoutDetails bottomRight;
}
