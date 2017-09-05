package com.projecten3.eva.Views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.projecten3.eva.Data.ApiService;
import com.projecten3.eva.Data.DbHelper;
import com.projecten3.eva.Data.EvaApiBuilder;
import com.projecten3.eva.Factory.ChallengeLevelFactory;
import com.projecten3.eva.Helpers.FilterChallengesForToday;
import com.projecten3.eva.Model.Challenge;
import com.projecten3.eva.Model.Challenges;
import com.projecten3.eva.Model.Day;
import com.projecten3.eva.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ChallengeActivity extends AppCompatActivity {
    private ArrayList<Challenge> todayAvailableChallenges;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Unbinder unbinder;
    private int currentDay;
    private Challenge challengeOfTheDay;
    private ChallengeLevelFactory levelFactory = new ChallengeLevelFactory();
    private boolean hasCompletedChallengeToday = false;
    private DbHelper dbHelper;

    @BindView(R.id.challengeTitleInCard)
    TextView challengeTitle;
    @BindView(R.id.challengeDesc)
    TextView challengeDescription;
    @BindView(R.id.daysTillRewardLayout)
    LinearLayout daysTillReward;
    @BindView(R.id.challengeLevel)
    TextView challengeLevel;
    @BindView(R.id.giveUp)
    Button giveUp;
    @BindView(R.id.finishChallenge)
    Button finishChallenge;
    @BindView(R.id.reroll)
    Button reroll;
    @BindView(R.id.hulp)
    Button help;
    @BindView(R.id.startButton)
    Button startChallenge;
    @BindView(R.id.countDownTimer)
    TextView countdownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);
        unbinder = ButterKnife.bind(this);

        getChallenges();
        updateUI();
        setDaysTillReward();
        setDaysLoggedInRow();





        dbHelper = new DbHelper(this);
        ArrayList<Day> al = dbHelper.getAll();
        for (Day d : al){
            Log.e("entry ---", d.getWhichDayOfTheChallenge() + "---" + d.getDayOfTheWeek() + "---" + d.getCompleted());
        }

    }

    /**
     * always start on the first possible index
     */
    private void updateUI() {
        if(todayAvailableChallenges != null && todayAvailableChallenges.size() > 0) {
            setTodaysChallenge(todayAvailableChallenges.get(0));
        }
        challengeLevel.setText(getResources().getString(levelFactory.getChallengeLevelString(currentDay)));


    }

    /**
     * method that rerolls to a random _today_ available challenge
     */
    @OnClick(R.id.reroll)
    public void rerollCurrentChallenge() {
        if(todayAvailableChallenges != null && todayAvailableChallenges.size() > 0) {
            Random random = new Random();
            int randomIndex = random.nextInt(todayAvailableChallenges.size());
            Log.i("random: ", "index =  " + randomIndex);
            setTodaysChallenge(todayAvailableChallenges.get(randomIndex));
        }
    }

    private void setDaysLoggedInRow() {

        SharedPreferences sharedPreferences = getSharedPreferences("days", Context.MODE_PRIVATE);
        currentDay = sharedPreferences.getInt("daysInRow",1);
    }

    private void setTodaysChallenge(Challenge challenge) {
        challengeOfTheDay = challenge;
        challengeTitle.setText(challenge.getTitel());
        challengeDescription.setText(challenge.getOmschrijving());

        //check challengeAlreadyStarted
        checkChallengeAlreadyStarted();
        /*
        ArrayList<DatabaseEntry> al = dbHelper.getAll();
        for (DatabaseEntry d : al){
            if (d.challenge.equals(String.valueOf(challengeOfTheDay.getId())) && d.state.equals("started") || d.state.equals("finished")){
                Log.e("already started","challenge aleady started or finished");
            }
        }*/
    }

    private void setDaysTillReward() {
        /*LayoutInflater inflater = getLayoutInflater();
        for(int i=0; i <= 4; i++) {
            View icon = inflater.inflate(R.layout.days_till_reward_check, null);
            TextView iconView = (TextView) icon.findViewById(R.id.reward_completed_or_not);
            if(i <= currentDay)
                iconView.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_check_circle));
            else
                iconView.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_remove_circle));

            daysTillReward.addView(icon);
        }*/
    }

    public void checkChallengeAlreadyStarted(){
        Log.e("getDay",String.valueOf(dbHelper.getDay(challengeOfTheDay.getDag()).getCompleted()));
        Log.e("checkChallenge","checkChallenge");
        if(dbHelper.getDay(challengeOfTheDay.getDag()).getCompleted() == 1 ){
            Log.e("checkChallenge","checkChallenge IF");
            startChallenge.setVisibility(View.GONE);
            StringBuilder sb = new StringBuilder(Long.toString(getHoursUntillMidnight()));
            sb.append(" ");
            sb.append(getResources().getString(R.string.hours_remaining));
            countdownTimer.setText(sb.toString());
            countdownTimer.setVisibility(View.VISIBLE);
            help.setVisibility(View.GONE);
            reroll.setVisibility(View.GONE);
            giveUp.setVisibility(View.VISIBLE);
            finishChallenge.setVisibility(View.VISIBLE);
        } else if (dbHelper.getDay(challengeOfTheDay.getDag()).getCompleted() == 2 ){
            Log.e("checkChallenge","checkChallenge ELSE IF");
            StringBuilder sb = new StringBuilder(getResources().getString(R.string.new_challenge));
            sb.append(" ");
            sb.append(Long.toString(getHoursUntillMidnight()));
            sb.append(" uren!");
            countdownTimer.setTextSize(15);
            countdownTimer.setText(sb.toString());
            hasCompletedChallengeToday=true;
            countdownTimer.setVisibility(View.VISIBLE);
            startChallenge.setVisibility(View.GONE);

        };

    }
    @OnClick(R.id.startButton)
    public void startChallenge() {

        startChallenge.setVisibility(View.GONE);
        StringBuilder sb = new StringBuilder(Long.toString(getHoursUntillMidnight()));
        sb.append(" ");
        sb.append(getResources().getString(R.string.hours_remaining));
        countdownTimer.setText(sb.toString());
        countdownTimer.setVisibility(View.VISIBLE);
        help.setVisibility(View.GONE);
        reroll.setVisibility(View.GONE);
        giveUp.setVisibility(View.VISIBLE);
        finishChallenge.setVisibility(View.VISIBLE);


        //challenge started
        dbHelper.updateState(String.valueOf(challengeOfTheDay.getDag()),1);
        /*
        Log.e("challengeoftheday---",String.valueOf(challengeOfTheDay.getId()));
        if(dbHelper.getData(challengeOfTheDay.getDag()).getCount() == 0){
            if(dbHelper.insertProgress(String.valueOf(challengeOfTheDay.getId()),String.valueOf(currentDay),"started")){
                Log.e("challengeAct","insertProgress");
                Toast.makeText(getApplicationContext(), "done",
                        Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(getApplicationContext(), "not done",
                        Toast.LENGTH_SHORT).show();
            }
        }*/

    }

    private long getHoursUntillMidnight() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        long milliseconsdTillMidnight = (c.getTimeInMillis()-System.currentTimeMillis());
        return TimeUnit.MILLISECONDS.toHours(milliseconsdTillMidnight);
    }

    @OnClick(R.id.finishChallenge)
    public void finishChallenge() {
        StringBuilder sb = new StringBuilder(getResources().getString(R.string.new_challenge));
        sb.append(" ");
        sb.append(Long.toString(getHoursUntillMidnight()));
        sb.append(" uren!");
        countdownTimer.setTextSize(15);
        countdownTimer.setText(sb.toString());
        hasCompletedChallengeToday=true;

       // dbHelper.updateProgress(String.valueOf(challengeOfTheDay.getId()),String.valueOf(currentDay),"finished");
        dbHelper.updateState(String.valueOf(challengeOfTheDay.getDag()),2);
        if(challengeOfTheDay.getReward() != null)
            createDialogForReward();

    }

    private void createDialogForReward() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.reward_dialog)
                .setPositiveButton(R.string.claim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.i("challengeactivity","bevestigd");
                    }
                });
        builder.create().show();
    }


    /**
     * get the challenges from the backend, this gets done asynchrounously on a specific IO thread (Schedulers.io())
     * it's observed on the mainThread, so when the results get in it can update/refresh the UI.
     *
     * compositedisposable is a container of disposable objects (the observable<Challenges>) to which you can add or remove
     * disposableobservers, this ties in really well with the android lifecycle: http://reactivex.io/RxJava/javadoc/io/reactivex/disposables/CompositeDisposable.html
     */
    private void getChallenges() {
        compositeDisposable.add(getObservableChallenges()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Challenges>() {
                    @Override
                    public void onNext(Challenges value) {
                        Log.i("onNext","retrieved 200 status");
                        todayAvailableChallenges =  new FilterChallengesForToday(value.getChallenges(), currentDay).getFilteredChallenges();
                        updateUI();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError","with error: \n");
                        e.printStackTrace();
                        Toast.makeText(getBaseContext(),R.string.error,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        Log.i("onComplete","Completed the call");
                    }
                }));
    }

    /**
     * an observable get request that only launches when it has a subscriber that needs it.
     * .defer docs: http://reactivex.io/documentation/operators/defer.html
     * this creates a new observable for every subscriber, and is easily cancable on screen rotation, back press, ...
     * @return
     */
    private Observable<Challenges> getObservableChallenges() {
        return Observable.defer(new Callable<ObservableSource<? extends Challenges>>() {
            @Override
            public ObservableSource<? extends Challenges> call() {
                ApiService service = EvaApiBuilder.getInstance();
                return service.getChallenges();
            }
        });
    }

    /**
     * in the onDestroy we clear and dispose of the compositedisposable, this causes the call to just terminate
     * this way no error occurs on screen rotation or a backpress when the server is trying to give the results to the UI thread
     * it will clear everything so there is no chance on memory leaks (like the unbinder in Butterknife)
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!compositeDisposable.isDisposed()){
            compositeDisposable.dispose();
        }
        if(unbinder != null)
            unbinder.unbind();
    }
}
