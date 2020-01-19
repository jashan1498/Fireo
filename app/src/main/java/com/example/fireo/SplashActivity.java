package com.example.fireo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fireo.Constants.Constants;
import com.example.fireo.Utils.LoginUtils;
import com.example.fireo.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

public class SplashActivity extends BaseApplication {

    private LoginUtils utils;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        utils = new LoginUtils(SplashActivity.this);
        boolean dummy = fetchUser();
        new Handler().postDelayed(()
                -> redirectUser(firebaseUser), 1000);
    }

    private boolean fetchUser() {
        if (firebaseUser != null) {
            getFireStore().collection(Constants.Collections.User).document(firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        user = task.getResult().toObject(User.class);
                    }
                }
            });
        }
        return user != null;
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
