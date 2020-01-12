package com.example.fireo;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fireo.Utils.SharedPrefUtils;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.auth.User;

public class BaseApplication extends AppCompatActivity {
    public static User user;
    public static FirebaseUser firebaseUser;
    public static FirebaseAuth firebaseAuth;
    public static FirebaseApp firebaseApp;
    SharedPrefUtils sharedPrefUtils;
    private FirebaseFirestore fireStore;

    BaseApplication() {
        setFirebaseSettings();
        initFirebase();


    }

    private void initFirebase() {
        firebaseApp = FirebaseApp.getInstance();
        firebaseAuth = new FirebaseAuth(firebaseApp);

        if (firebaseAuth.getCurrentUser() != null) {
            firebaseUser = firebaseAuth.getCurrentUser();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPrefUtils = new SharedPrefUtils(this);
        applyTheme(sharedPrefUtils.getDayNightMode());
    }

    private void setFirebaseSettings() {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .build();
        fireStore = FirebaseFirestore.getInstance();
        fireStore.setFirestoreSettings(settings);
    }

    public void applyTheme(boolean isNightMode) {
        if (isNightMode) {
            setTheme(R.style.DarkAppTheme);
        } else {
            setTheme(R.style.LightAppTheme);
        }
    }

    public void Logout(){
        FirebaseAuth.getInstance().signOut();
        firebaseUser = null;
        this.finishAffinity();
        Intent redirectToBase = new Intent(this,SplashActivity.class);
        startActivity(redirectToBase);

    }

}
