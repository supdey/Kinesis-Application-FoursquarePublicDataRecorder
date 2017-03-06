/*
 * Developed by Alex Grace for research purposes only. (ag00248@surrey.ac.uk)
 */

package com.alexgrace.finalyearproject.kinesisclient.FoursquareEntities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Venue {
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Contact getContact() {
        return contact;
    }
    public Location getLocation() {
        return location;
    }
    public List<Categories> getCategories() {
        return categories;
    }
    public boolean getVerified() {
        return verified;
    }
    public Stats getStats() {
        return stats;
    }
    public boolean getClosed() {
        return closed;
    }
    public String getUrl() {
        return url;
    }
    public boolean getHasMenu() {
        return hasMenu;
    }
    public Delivery getDelivery() {
        return delivery;
    }
    public Menu getMenu() {
        return menu;
    }
    public boolean getPrivate() {
        return fsqprivate;
    }
    public boolean getAllowMenuUrlEdit() {
        return allowMenuUrlEdit;
    }
    public boolean getVenueRatingBlacklisted() {
        return venueRatingBlacklisted;
    }
    public BeenHere getBeenHere() {
        return beenHere;
    }
    public Events getEvents() {
        return events;
    }
    public VenuePage getVenuePage() {
        return venuePage;
    }
    public String getStoreId() {
        return storeId;
    }

    private String id;
    private String name;
    private Contact contact;
    private Location location;
    private List<Categories> categories;
    private boolean verified;
    private Stats stats;
    private boolean closed;
    private String url;
    private boolean hasMenu;
    private Delivery delivery;
    private Menu menu;
    @JsonProperty(value = "private")
    private boolean fsqprivate;
    private boolean allowMenuUrlEdit;
    private boolean venueRatingBlacklisted;
    private BeenHere beenHere;
    private Events events;
    private VenuePage venuePage;
    private String storeId;
}
