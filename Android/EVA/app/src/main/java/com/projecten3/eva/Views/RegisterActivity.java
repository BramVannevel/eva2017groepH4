package com.projecten3.eva.Views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.projecten3.eva.Data.ApiService;
import com.projecten3.eva.Data.EvaApiBuilder;
import com.projecten3.eva.Model.Authenticate;
import com.projecten3.eva.Model.Register;
import com.projecten3.eva.R;

import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Unbinder unbinder;

    @BindView(R.id.register_input_email)
    EditText email;
    @BindView(R.id.register_input_password)
    EditText passwoord;
    @BindView(R.id.register_input_password_repeat)
    EditText passwordRepeat;
    @BindView(R.id.register_error)
    TextView registerError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        unbinder = ButterKnife.bind(this);
    }

    @OnClick(R.id.register_btn_login)
    public void signUp() {
        Log.i("register","clicked on signup");
        String emailString = email.getText().toString();
        String paswordString = passwoord.getText().toString();
        String repeatedPW = passwordRepeat.getText().toString();

        if(emailString.length() < 5 || paswordString.length() < 0 || repeatedPW.length() < 5) {
            Log.i("register","in if lengte check");
            registerError.setText("Naam en paswoord moeten langer zijn dan 5 karakters");
        } else  if (!paswordString.equals(repeatedPW)) {
            Log.i("register","in if lengte pw check");
            registerError.setText("paswoorden zijn niet gelijk");
        } else {
            Log.i("register","in else van controle");
            register(emailString, paswordString);
        }
    }
    /**
     * get the challenges from the backend, this gets done asynchrounously on a specific IO thread (Schedulers.io())
     * it's observed on the mainThread, so when the results get in it can update/refresh the UI.
     *
     * compositedisposable is a container of disposable objects (the observable<Challenges>) to which you can add or remove
     * disposableobservers, this ties in really well with the android lifecycle: http://reactivex.io/RxJava/javadoc/io/reactivex/disposables/CompositeDisposable.html
     */
    private void register(final String username, final String password) {
        compositeDisposable.add(registerResponse(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Register>() {
                    @Override
                    public void onNext(Register value) {
                            Log.i("registreer","succes");
                            login(username,password);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("registreer","error:");
                        e.printStackTrace();
                        Toast.makeText(RegisterActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() {
                    }
                }));
    }


    /**
     * an observable get request that only launches when it has a subscriber that needs it.
     * .defer docs: http://reactivex.io/documentation/operators/defer.html
     * this creates a new observable for every subscriber, and is easily cancable on screen rotation, back press, ...
     * @return
     */
    private Observable<Register> registerResponse(final String username, final String password) {
        return Observable.defer(new Callable<ObservableSource<? extends Register>>() {
            @Override
            public ObservableSource<? extends Register> call() {
                ApiService service = EvaApiBuilder.getInstance();
                return service.signUp(username, password);
            }
        });
    }

    /**
     * get the challenges from the backend, this gets done asynchrounously on a specific IO thread (Schedulers.io())
     * it's observed on the mainThread, so when the results get in it can update/refresh the UI.
     *
     * compositedisposable is a container of disposable objects (the observable<Challenges>) to which you can add or remove
     * disposableobservers, this ties in really well with the android lifecycle: http://reactivex.io/RxJava/javadoc/io/reactivex/disposables/CompositeDisposable.html
     */
    private void login(String username, String password) {
        compositeDisposable.add(loginResponse(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Authenticate>() {
                    @Override
                    public void onNext(Authenticate value) {
                        if(value.getToken() != null && !value.getToken().isEmpty()) {
                            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("token", value.getToken());
                            editor.putBoolean("isLoggedIn", true);
                            editor.commit();
                            startMainActivity();
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(RegisterActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() {
                    }
                }));
    }

    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }
    /**
     * an observable get request that only launches when it has a subscriber that needs it.
     * .defer docs: http://reactivex.io/documentation/operators/defer.html
     * this creates a new observable for every subscriber, and is easily cancable on screen rotation, back press, ...
     * @return
     */
    private Observable<Authenticate> loginResponse(final String username, final String password) {
        return Observable.defer(new Callable<ObservableSource<? extends Authenticate>>() {
            @Override
            public ObservableSource<? extends Authenticate> call() {
                ApiService service = EvaApiBuilder.getInstance();
                return service.authenticate(username, password);
            }
        });
    }
}
