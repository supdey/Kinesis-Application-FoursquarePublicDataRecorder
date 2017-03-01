/*
 * Developed by Alex Grace for research purposes only. (ag00248@surrey.ac.uk)
 */

package com.alexgrace.finalyearproject.kinesisclient.entities;

public class StickerPowerup {
    public String getBonusType() {
        return bonusType;
    }
    public int getValue() {
        return value;
    }
    public String getTitle() {
        return title;
    }
    public boolean getShowUpgradeLink() {
        return showUpgradeLink;
    }

    private String bonusType;
    private int value;
    private String title;
    private boolean showUpgradeLink;
}
