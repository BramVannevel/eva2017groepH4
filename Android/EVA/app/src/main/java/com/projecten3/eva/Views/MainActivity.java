package com.projecten3.eva.Views;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.projecten3.eva.Adapters.ChallengeDaysAdapter;
import com.projecten3.eva.Data.DbHelper;
import com.projecten3.eva.Factory.DaysOfWeekFactory;
import com.projecten3.eva.Model.Day;
import com.projecten3.eva.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private RecyclerView.LayoutManager layoutManager;
    private ChallengeDaysAdapter adapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Unbinder unbinder;
    private int currentDay;
    private DbHelper dbHelper;

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
                Log.i(TAG, "onerror"
            }
        });*/

        dbHelper = new DbHelper(this);
        unbinder = ButterKnife.bind(this);

        ArrayList<Day> al = new ArrayList<>();
        al = dbHelper.getAll();
        for (Day d : al) {
            Log.e("dayentr", d.getWhichDayOfTheChallenge() + " " + d.getCompleted());
        }
        initMainUI();
    }

    private void initMainUI() {
        layoutManager = new LinearLayoutManager(this.getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        adapter = new ChallengeDaysAdapter(initChallengeDays());
        rvDays.setLayoutManager(layoutManager);
        rvDays.setAdapter(adapter);
    }

    /**
     * can't use enhanced for on int :(, get the list for the days of the week on first launch
     * @return
     */
    private ArrayList<Day> initChallengeDays(){

        ArrayList<Day> days = new ArrayList<>();
        Log.e("currentday", String.valueOf(currentDay));
        if(checkDaysLoggedInRow()){
            Log.e("MainActivity", "initChallenges if");
            days = dbHelper.getAll();
        } else {
            dbHelper.dropTable();
            Log.e("MainActivity", "initChallenges else");
            for (int i = 1; i < 21; i++) {
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                Log.e("currentDayInitChall", String.valueOf(currentDay));
                c.add(Calendar.DAY_OF_YEAR, i - currentDay);
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                Log.e("dayOfweek---", String.valueOf(dayOfWeek));
                days.add(new Day(getShortenedDayOfWeek(dayOfWeek), i, 0));
                dbHelper.insertProgress(getShortenedDayOfWeek(dayOfWeek), String.valueOf(i), "0");
            }

        }
        Log.e("currentday", String.valueOf(currentDay));
        return days;
    }

    /**
     * retrieve shortened day names in the correct language trough string resources via a factory.
     * @param day
     * @return
     */
    private String getShortenedDayOfWeek(int day) {
        DaysOfWeekFactory factory = new DaysOfWeekFactory();
        return getResources().getString(factory.getShortNameFromDayOfWeek(day));
    }

    private boolean checkDaysLoggedInRow(){

        boolean daysinRow = false;
        SharedPreferences sharedPreferences = getSharedPreferences("days", Context.MODE_PRIVATE);


        Calendar c = Calendar.getInstance();
        int today = c.get(Calendar.DAY_OF_YEAR);
        int lastDay = sharedPreferences.getInt("daysDate",0);
        currentDay = sharedPreferences.getInt("daysInRow",1);
        Log.i("today -------", String.valueOf(today));
        Log.i("last day -------", String.valueOf(lastDay));
        if (lastDay == today -1){
            currentDay +=1;
            sharedPreferences.edit().putInt("daysDate",today).commit();
            sharedPreferences.edit().putInt("daysInRow",currentDay).commit();
            daysinRow = true;

        } else if(lastDay == today){
            //niks doen, nog dezelfde dag
            daysinRow = true;
        }else {
            currentDay = 1;
            sharedPreferences.edit().putInt("daysDate",today).commit();
            sharedPreferences.edit().putInt("daysInRow",currentDay).commit();
            daysinRow = false;

        }
        return daysinRow;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if(!compositeDisposable.isDisposed() && compositeDisposable != null) {
            compositeDisposable.clear();
            compositeDisposable.dispose();
        }
    }
}
