/*
 * Developed by Alex Grace for research purposes only. (ag00248@surrey.ac.uk)
 */

package com.alexgrace.finalyearproject.kinesisclient.entities;

public class Items {
    public String getId() {
        return id;
    }
    public long getCreatedAt() {
        return createdAt;
    }
    public boolean getHasSticker() {
        return hasSticker;
    }
    public String getPrefix() {
        return prefix;
    }
    public String getSuffix() {
        return suffix;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public User getUser() {
        return user;
    }
    public String getVisibility() {
        return visibility;
    }

    private String id;
    private long createdAt;
    private boolean hasSticker;
    private String prefix;
    private String suffix;
    private int width;
    private int height;
    private User user;
    private String visibility;
}
