package com.projecten3.eva.Model;

/**
 * model class for the 21 days of the challenge
 */
public class Day {
    private String dayOfTheWeek;
    private int whichDayOfTheChallenge;
    private boolean completed;

    public Day(String dayOfTheWeek, int whichDayOfTheChallenge, boolean completed) {
        this.dayOfTheWeek = dayOfTheWeek;
        this.whichDayOfTheChallenge = whichDayOfTheChallenge;
        this.completed = completed;
    }

    public String getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public int getWhichDayOfTheChallenge() {
        return whichDayOfTheChallenge;
    }

    public boolean isCompleted() {
        return completed;
    }
}
