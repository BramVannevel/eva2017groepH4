package com.projecten3.eva.Helpers;

import android.content.Context;

import com.projecten3.eva.Model.CoreButtons;
import com.projecten3.eva.R;

import java.util.ArrayList;

/**
 * helper class to init core home buttons, moved to helper class for future proofing and flexibility/maintainability
 */
public class CreateCoreButtons {
    private ArrayList<CoreButtons> buttons = new ArrayList<>();
    private Context context;

    public CreateCoreButtons(Context context) {
        this.context = context;
    }

    private void initButtons() {
        buttons.add(new CoreButtons(R.drawable.ic_today,context.getResources().getString(R.string.todays_challenge)));
        buttons.add(new CoreButtons(R.drawable.ic_filter_vintage,context.getResources().getString(R.string.vegagram)));
        buttons.add(new CoreButtons(R.drawable.ic_lightbulb_outline,context.getResources().getString(R.string.tips_and_tricks)));
        buttons.add(new CoreButtons(R.drawable.ic_restaurant_menu,context.getResources().getString(R.string.nearby_restaurants)));
    }

    public ArrayList<CoreButtons> getButtons() {
        initButtons();
        return buttons;
    }
}
