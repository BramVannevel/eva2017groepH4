package com.projecten3.eva.Factory;

import com.projecten3.eva.R;

public class ChallengeLevelFactory {
    public int getChallengeLevelString(int day) {
        int weight = (int) Math.ceil(day/5d);
        switch(weight) {
            case 1: return R.string.easy;
            case 2: return R.string.medium;
            case 3: return R.string.hard;
            default: return R.string.hard;
        }
    }
}
