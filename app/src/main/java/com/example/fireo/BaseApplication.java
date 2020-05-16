package com.example.fireo;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fireo.Constants.Constants;
import com.example.fireo.Utils.LoginUtils;
import com.example.fireo.Utils.SharedPrefUtils;
import com.example.fireo.activities.SplashActivity;
import com.example.fireo.model.Building;
import com.example.fireo.model.Buildings;
import com.example.fireo.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class BaseApplication extends AppCompatActivity {
    public static User user;
    public static FirebaseUser firebaseUser;
    public static FirebaseAuth firebaseAuth;
    public static FirebaseApp firebaseApp;
    public static Buildings buildingsList = new Buildings();
    public static Building currentBuilding = new Building();
    private FirebaseFirestore fireStore;

    public BaseApplication() {
        setFirebaseSettings();
        initFirebase();
    }

    public static FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    public static FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public static FirebaseApp getFirebaseApp() {
        return firebaseApp;
    }

    public FirebaseFirestore getFireStore() {
        return fireStore;
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
        SharedPrefUtils sharedPrefUtils = new SharedPrefUtils(this);
        String buildingId = sharedPrefUtils.retrieve(SharedPrefUtils.CURRENT_BUILDING);
        if (buildingId != null) {
            fetchCurrentBuilding(buildingId);
        }
        applyTheme(sharedPrefUtils.getDayNightMode());
    }

    private void fetchCurrentBuilding(String buildingId) {
        fireStore.collection(Constants.Collections.BUILDING).document(buildingId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Building building = task.getResult().toObject(Building.class);
                    if (building.getBuildingId() != null) {
                        currentBuilding = building;
                    }
                }
            }
        });
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

    public void logout() {
        SharedPrefUtils sharedPrefUtils = new SharedPrefUtils(this);
        LoginUtils loginUtils = new LoginUtils(this);

        buildingsList.getBuildingsList().clear();
        loginUtils.logout();
        currentBuilding = null;
        sharedPrefUtils.save(SharedPrefUtils.CURRENT_BUILDING, null);
        this.finishAffinity();
        Intent redirectToBase = new Intent(this, SplashActivity.class);
        startActivity(redirectToBase);
        FirebaseAuth.getInstance().signOut();

    }
}
