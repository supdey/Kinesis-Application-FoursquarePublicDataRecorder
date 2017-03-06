/*
 * Developed by Alex Grace for research purposes only. (ag00248@surrey.ac.uk)
 */

package com.alexgrace.finalyearproject.kinesisclient.FoursquareEntities;

import java.util.List;

public class EventItem {
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public List<Categories> getCategories() {
        return categories;
    }
    public long getStartAt() {
        return startAt;
    }
    public long getEndAt() {
        return endAt;
    }
    public boolean getAllDay() {
        return allDay;
    }
    public String getTimeZone() {
        return timeZone;
    }
    public String getText() {
        return text;
    }
    public String getUrl() {
        return url;
    }
    public List<Image> getImages() {
        return images;
    }
    public Provider getProvider() {
        return provider;
    }
    public Stats getStats() {
        return stats;
    }

    private String id;
    private String name;
    private List<Categories> categories;
    private long startAt;
    private long endAt;
    private boolean allDay;
    private String timeZone;
    private String text;
    private String url;
    private List<Image> images;
    private Provider provider;
    private Stats stats;
}
