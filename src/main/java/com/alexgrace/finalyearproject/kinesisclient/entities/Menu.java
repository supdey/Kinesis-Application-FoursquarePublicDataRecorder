/*
 * Developed by Alex Grace for research purposes only. (ag00248@surrey.ac.uk)
 */

package com.alexgrace.finalyearproject.kinesisclient.entities;

public class Menu {
    public String getType() {
        return type;
    }
    public String getLabel() {
        return label;
    }
    public String getAnchor() {
        return label;
    }
    public String getUrl() {
        return url;
    }
    public String getMobileUrl() {
        return mobileUrl;
    }
    public String getExternalUrl() {
        return externalUrl;
    }

    private String type;
    private String label;
    private String anchor;
    private String url;
    private String mobileUrl;
    private String externalUrl;
}
