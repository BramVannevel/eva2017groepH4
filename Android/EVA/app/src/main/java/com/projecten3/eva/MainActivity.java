package com.projecten3.eva;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private Unbinder unbinder;

    @BindView(R.id.fb_btn_login)
    LoginButton facebookLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        facebookLoginCallback();
    }

    /**
     * basis implementatie van de callback van de facebook login activity zoals gezien op dev.fb.com
     */
    private void facebookLoginCallback(){
        CallbackManager callbackManager = CallbackManager.Factory.create();
        facebookLogin.setReadPermissions("email");
        facebookLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i(TAG, "onsucces");
            }

            @Override
            public void onCancel() {
                Log.i(TAG, "oncancel");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.i(TAG, "onerror");
            }
        });
    }

    /**
     * onclick van Butterknife verwacht een public methode
     */
    @OnClick(R.id.btn_sign_up)
    public void onSignUpClickListener(){
        startActivity(new Intent(this, RegisterActivity.class));
    }

    /**
     * unbind butterknife in the ondestroy to avoid memory leaks
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(unbinder != null)
            unbinder.unbind();
    }
}
