package com.projecten3.eva.Views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.projecten3.eva.Data.ApiService;
import com.projecten3.eva.Data.EvaApiBuilder;
import com.projecten3.eva.Helpers.FilterChallengesForToday;
import com.projecten3.eva.Model.Authenticate;
import com.projecten3.eva.Model.Challenges;
import com.projecten3.eva.R;

import java.util.concurrent.Callable;

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

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private CallbackManager callbackManager;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private boolean isLoggedIn = false;
    private Unbinder unbinder;

    @BindView(R.id.input_email)
    EditText email;
    @BindView(R.id.input_password)
    EditText paswoord;
    @BindView(R.id.error)
    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        unbinder = ButterKnife.bind(this);
        checkIsLoggedIn();
        setFBListener();
    }

    private void checkIsLoggedIn() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPref.getBoolean("isLoggedIn",false);

        if(isLoggedIn) {
            Log.i(TAG,"is logged in");
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    private void setFBListener() {
        LoginButton loginButton = (LoginButton) findViewById(R.id.fb_btn_login);
        CallbackManager callbackManager = CallbackManager.Factory.create();

        loginButton.setReadPermissions("email");
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
        });
    }

    @OnClick(R.id.btn_sign_up)
    public void signUp() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @OnClick(R.id.btn_login_login)
    public void loginClicked(){
        Log.i(TAG,"clicked on login");
        String emailString = email.getText().toString();
        String paswoordString = paswoord.getText().toString();
        if(emailString.isEmpty() || paswoordString.isEmpty())
            error.setText(R.string.error_login);
        else
            login(emailString, paswoordString);
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
                            Log.i(TAG,value.getToken());
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
                        Toast.makeText(LoginActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if(!compositeDisposable.isDisposed())
            compositeDisposable.dispose();
    }
}
