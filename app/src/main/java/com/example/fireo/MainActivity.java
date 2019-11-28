package com.example.fireo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fireo.Utils.LoginListener;
import com.example.fireo.Utils.LoginUtils;

public class MainActivity extends AppCompatActivity implements LoginListener {

    LoginUtils utils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        utils = new LoginUtils(this);
        utils.setLoginListener(this);
        utils.setLoginState("boom", true);

    }

    @Override
    public void onLoginChanged(String user, boolean isLoggedIn) {
        Intent intent;
        if (isLoggedIn && user != null) {
            intent = new Intent(MainActivity.this, HomeActivity.class);
        } else {
            intent = new Intent(MainActivity.this, LoginActivity.class);
        }
        startActivity(intent);
        Toast.makeText(this, "" + utils.getUsername(), Toast.LENGTH_SHORT).show();
    }
}
