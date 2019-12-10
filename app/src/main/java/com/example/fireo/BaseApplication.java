package com.example.fireo;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class BaseApplication extends AppCompatActivity {

    BaseApplication() {
        setFirebaseSettings();
    }

    private void setFirebaseSettings() {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .build();
        FirebaseFirestore d = FirebaseFirestore.getInstance();
        d.setFirestoreSettings(settings);
    }


}
