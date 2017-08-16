package com.projecten3.eva.Model;

/**
 * model class for the 21 days of the challenge
 */
public class Day {


    private String dayOfTheWeek;
    private int whichDayOfTheChallenge;
    private int completed;

    public Day(){};
    public Day(String dayOfTheWeek, int whichDayOfTheChallenge, int completed) {
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

    public int getCompleted() {
        return completed;
    }

    public void setDayOfTheWeek(String dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }

    public void setWhichDayOfTheChallenge(int whichDayOfTheChallenge) {
        this.whichDayOfTheChallenge = whichDayOfTheChallenge;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }
}
