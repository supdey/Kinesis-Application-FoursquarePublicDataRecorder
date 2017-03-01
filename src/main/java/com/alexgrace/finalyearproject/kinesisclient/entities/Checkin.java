/*
 * Developed by Alex Grace for research purposes only. (ag00248@surrey.ac.uk)
 */

package com.alexgrace.finalyearproject.kinesisclient.entities;

import java.util.List;

public class Checkin {

    public String getId() {
        return id;
    }
    public long getCreatedAt() {
        return createdAt;
    }
    public String getType() {
        return type;
    }
    public List<Entities> getEntities() {
        return entities;
    }
    public String getShout() {
        return shout;
    }
    public int getTimeZoneOffset() {
        return timeZoneOffset;
    }
    public List<User> getWith() {
        return with;
    }
    public User getUser() {
        return user;
    }
    public Venue getVenue() {
        return venue;
    }
    public Source getSource() {
        return source;
    }
    public Photos getPhotos() {
        return photos;
    }
    public Event getEvent() {
        return event;
    }
    public Likes getLikes() {
        return likes;
    }
    public Sticker getSticker() {
        return sticker;
    }
    public boolean getIsMayor() {
        return isMayor;
    }
    public Score getScore() {
        return score;
    }
    public StickerPowerup getStickerPowerup() {
        return stickerPowerup;
    }
    public String getReasonCannotSeeComments() {
        return reasonCannotSeeComments;
    }
    public String getReasonCannotAddComments() {
        return reasonCannotAddComments;
    }
    public List<Sticker> getUnlockedStickers() {
        return unlockedStickers;
    }

    private String id;
    private long createdAt;
    private String type;
    private List<Entities> entities;
    private String shout;
    private int timeZoneOffset;
    private List<User> with;
    private User user;
    private Venue venue;
    private Source source;
    private Photos photos;
    private Event event;
    private Likes likes;
    private Sticker sticker;
    private boolean isMayor;
    private Score score;
    private StickerPowerup stickerPowerup;
    private String reasonCannotSeeComments;
    private String reasonCannotAddComments;
    private List<Sticker> unlockedStickers;
}
