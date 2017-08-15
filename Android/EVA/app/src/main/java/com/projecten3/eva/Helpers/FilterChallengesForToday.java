package com.projecten3.eva.Helpers;

import com.projecten3.eva.Model.Challenge;

import java.util.ArrayList;

public class FilterChallengesForToday {
    private ArrayList<Challenge> filteredChallenges = new ArrayList<>();
    private int day;
    private ArrayList<Challenge> allChallenges;

    public FilterChallengesForToday(ArrayList<Challenge> allChallenges, int day) {
        this.allChallenges = allChallenges;
        this.day = day;
    }

    public ArrayList<Challenge> getFilteredChallenges() {
        filterAllChallenges();
        return filteredChallenges;
    }

    private void filterAllChallenges() {
        for (Challenge challenge: allChallenges) {
            if (challenge.getDag() == day)
                filteredChallenges.add(challenge);
        }
    }
}
