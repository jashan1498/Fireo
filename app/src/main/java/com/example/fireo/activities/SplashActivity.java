package com.example.fireo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.fireo.BaseApplication;
import com.example.fireo.Constants.Constants;
import com.example.fireo.R;
import com.example.fireo.Utils.LoginUtils;
import com.example.fireo.model.Building;
import com.example.fireo.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

public class SplashActivity extends BaseApplication {

    private LoginUtils utils;
    ImageView gifView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        gifView = findViewById(R.id.gif_view);
        Glide.with(this).asGif().load(R.drawable.lit_extinguisher).into(gifView);
        utils = new LoginUtils(SplashActivity.this);
        fetchUser();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                redirectUser(firebaseUser);
            }
        }, 2000);

    }


    private boolean fetchUser() {
        if (firebaseUser != null) {
            getFireStore().collection(Constants.Collections.USER).document(firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        user = task.getResult().toObject(User.class);
                        if (user != null) {
                            if (user.getBuildingList().size() > 0) {
                                fetchBuildingNames();
                            }
                        }
                    }
                }
            });
        }
        return user != null;

    }

    private void fetchBuildingNames() {
        for (int x = 0; x < user.getBuildingList().size(); x++) {
            getFireStore().collection(Constants.Collections.BUILDING).document(user.getBuildingList().get(x)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    Building building = task.getResult().toObject(Building.class);
                    if (building.getBuildingId() != null) {
                        BaseApplication.buildingsList.addBuilding(building);
                    }
                }
            });
        }
    }

    private void redirectUser(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);

        } else {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        this.finish();
    }
}
