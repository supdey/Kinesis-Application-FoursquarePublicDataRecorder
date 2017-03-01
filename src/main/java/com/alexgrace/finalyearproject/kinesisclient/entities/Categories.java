/*
 * Developed by Alex Grace for research purposes only. (ag00248@surrey.ac.uk)
 */

package com.alexgrace.finalyearproject.kinesisclient.entities;

public class Categories {
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getPluralName() {
        return pluralName;
    }
    public String getShortName() {
        return shortName;
    }
    public Icon getIcon() {
        return icon;
    }
    public boolean getPrimary() {
        return primary;
    }

    private String id;
    private String name;
    private String pluralName;
    private String shortName;
    private Icon icon;
    private boolean primary;
}
