/*
 * Developed by Alex Grace for research purposes only. (ag00248@surrey.ac.uk)
 */

package com.alexgrace.finalyearproject.kinesisclient.FoursquareEntities;

import java.util.List;

public class Score {
    public int getTotal() {
        return total;
    }
    public List<Scores> getScores() {
        return scores;
    }

    private int total;
    private List<Scores> scores;
}
