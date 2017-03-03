/*
 * Developed by Alex Grace for research purposes only. (ag00248@surrey.ac.uk)
 */

package com.alexgrace.finalyearproject.kinesisclient.entities;

import java.util.List;

public class Sticker {
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Image getImage() {
        return image;
    }
    public List<Effects> getEffects() {
        return effects;
    }
    public String getStickerType() {
        return stickerType;
    }
    public FsqGroup getGroup() {
        return group;
    }
    public PickerPosition getPickerPosition() {
        return pickerPosition;
    }
    public String getTeaseText() {
        return teaseText;
    }
    public String getUnlockText() {
        return unlockText;
    }
    public String getBonusText() {
        return bonusText;
    }
    public int getPoints() {
        return points;
    }
    public String getBonusStatus() {
        return bonusStatus;
    }

    private String id;
    private String name;
    private Image image;
    private List<Effects> effects;
    private String stickerType;
    private FsqGroup group;
    private PickerPosition pickerPosition;
    private String teaseText;
    private String unlockText;
    private String bonusText;
    private int points;
    private String bonusStatus;
}
