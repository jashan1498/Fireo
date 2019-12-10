package com.example.fireo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fireo.Utils.LoginListener;
import com.example.fireo.Utils.LoginUtils;

public class SplashActivity extends AppCompatActivity implements LoginListener {

    LoginUtils utils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        utils = new LoginUtils(SplashActivity.this);
        utils.setLoginListener(SplashActivity.this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                utils.setLoginState("boom", true);
            }
        }, 1000);
    }

    @Override
    public void onLoginChanged(String user, boolean isLoggedIn) {
        Intent intent;
        if (isLoggedIn && user != null) {
            intent = new Intent(SplashActivity.this, MainActivity.class);
        } else {
            intent = new Intent(SplashActivity.this, LoginActivity.class);
        }
        startActivity(intent);
        this.finish();
        Toast.makeText(this, "" + utils.getUsername(), Toast.LENGTH_SHORT).show();
    }
}
