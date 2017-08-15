package com.projecten3.eva.Factory;

import com.projecten3.eva.R;

public class DaysOfWeekFactory {
    public int getShortNameFromDayOfWeek(int day) {
        switch(day) {
            case 1: return R.string.sunday;
            case 2: return R.string.monday;
            case 3: return R.string.tuesday;
            case 4: return R.string.wednesday;
            case 5: return R.string.thursday;
            case 6: return R.string.friday;
            case 7: return R.string.saturday;
            default: return R.string.not_found;
        }
    }
}
