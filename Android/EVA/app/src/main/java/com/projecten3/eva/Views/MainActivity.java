package com.projecten3.eva.Views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.projecten3.eva.Data.ApiService;
import com.projecten3.eva.Data.DbHelper;
import com.projecten3.eva.Data.EvaApiBuilder;
import com.projecten3.eva.Factory.DaysOfWeekFactory;
import com.projecten3.eva.Model.DatabaseEntry;
import com.projecten3.eva.Model.Day;
import com.projecten3.eva.Model.Restaurant;
import com.projecten3.eva.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

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

        checkDaysLoggedInRow();
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
        for(int i = 1; i < 21; i++){
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            Log.e("currentDayInitChall",String.valueOf(currentDay));
            c.add(Calendar.DAY_OF_YEAR, i-currentDay);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            Log.e("dayOfweek---",String.valueOf(dayOfWeek));
            days.add(new Day(getShortenedDayOfWeek(dayOfWeek),i,false));
          /*for(Day d : days){
                if(d.getWhichDayOfTheChallenge()<currentDay && dbHelper.getDataByDay(String.valueOf(currentDay)).contains(d.getWhichDayOfTheChallenge())){
                    Log.e("dayofchallengeKl",d.getDayOfTheWeek() + String.valueOf(d.getWhichDayOfTheChallenge()) + "completed");
                    //image van d vervangen door een checkmark

                }
            }*/
            ArrayList<String> test = dbHelper.getDataByDay(String.valueOf(currentDay));
          for (Day d : days){
            //  Log.i("dayweek",String.valueOf(d.getWhichDayOfTheChallenge()));
              if (test.contains(String.valueOf(d.getWhichDayOfTheChallenge()))){
                  Log.e("day of the challenge ",String.valueOf(d.getWhichDayOfTheChallenge() + " completed"));

              }
          }



/*
          ArrayList<DatabaseEntry> test = dbHelper.getAll();
            for (DatabaseEntry t : test){
                if (t.day.equals(String.valueOf(currentDay)) && t.state.equals("finished")){
                    Log.e("completed for ", getShortenedDayOfWeek(Integer.valueOf(t.day)));
                }
            }*/
            /*
          ArrayList<DatabaseEntry> dbe = dbHelper.getDataByDay(String.valueOf(currentDay));
            for (DatabaseEntry e : dbe){
                Log.e("dag",e.day);
            }*/
        }
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

    private void checkDaysLoggedInRow(){

        SharedPreferences sharedPreferences = getSharedPreferences("days", Context.MODE_PRIVATE);


        Calendar c = Calendar.getInstance();
        int today = c.get(Calendar.DAY_OF_YEAR);
        int lastDay = sharedPreferences.getInt("daysDate",0);
        currentDay = sharedPreferences.getInt("daysInRow",0);

        if (lastDay == today -1){
            currentDay +=1;
            sharedPreferences.edit().putInt("daysDate",today).commit();
            sharedPreferences.edit().putInt("daysInRow",currentDay).commit();

        } else if(lastDay == today){
            //niks doen, nog dezelfde dag
        }else {
            currentDay = 1;
            sharedPreferences.edit().putInt("daysDate",today).commit();
            sharedPreferences.edit().putInt("daysInRow",currentDay).commit();

        }
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
