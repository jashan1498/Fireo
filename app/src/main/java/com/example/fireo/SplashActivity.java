package com.example.fireo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

import com.example.fireo.Utils.LoginUtils;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends BaseApplication {

    LoginUtils utils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        utils = new LoginUtils(SplashActivity.this);
        new Handler().postDelayed(()
                -> redirectUser(firebaseUser), 1000);
    }

    private void redirectUser(FirebaseUser user) {
        Intent intent;
        if (user != null) {
            intent = new Intent(SplashActivity.this, MainActivity.class);
        } else {
            intent = new Intent(SplashActivity.this, LoginActivity.class);
        }
        startActivity(intent);
        this.finish();
    }
}
