/*
 * Developed by Alex Grace for research purposes only. (ag00248@surrey.ac.uk)
 */

package com.alexgrace.finalyearproject.kinesisclient.entities;

public class Stats {
    public int getCheckinsCount() {
        return checkinsCount;
    }
    public int getUsersCount() {
        return usersCount;
    }
    public int getTipCount() {
        return tipCount;
    }

    private int checkinsCount;
    private int usersCount;
    private int tipCount;
}
