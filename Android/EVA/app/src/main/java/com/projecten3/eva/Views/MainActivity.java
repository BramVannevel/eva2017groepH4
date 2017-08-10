package com.projecten3.eva.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.projecten3.eva.Adapters.ChallengeDaysAdapter;
import com.projecten3.eva.Factory.DaysOfWeekFactory;
import com.projecten3.eva.Model.Day;
import com.projecten3.eva.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private RecyclerView.LayoutManager layoutManager;
    private ChallengeDaysAdapter adapter;

    @BindView(R.id.rv_core_layout)
    RecyclerView rvDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        //CallbackManager callbackManager = CallbackManager.Factory.create();

        /*loginButton.setReadPermissions("email");
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.i(TAG, "onsucces");
            }

            @Override
            public void onCancel() {
                // App code
                Log.i(TAG, "oncancel");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.i(TAG, "onerror");
            }
        });*/
        ButterKnife.bind(this);
        layoutManager = new LinearLayoutManager(this.getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        adapter = new ChallengeDaysAdapter(initChallengeDays());
        rvDays.setLayoutManager(layoutManager);
        rvDays.setAdapter(adapter);
    }

    private ArrayList<Day> initChallengeDays(){
        ArrayList<Day> days = new ArrayList<>();
        for(int i = 0; i < 21; i++){
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.DATE, i);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            days.add(new Day(getShortenedDayOfWeek(dayOfWeek),i+1,false));
        }
        return days;
    }

    private String getShortenedDayOfWeek(int day) {
        DaysOfWeekFactory factory = new DaysOfWeekFactory();
        return getResources().getString(factory.getShortNameFromDayOfWeek(day));
    }
}
