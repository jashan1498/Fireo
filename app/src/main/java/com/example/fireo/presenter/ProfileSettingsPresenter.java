package com.example.fireo.presenter;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.fireo.BaseApplication;
import com.example.fireo.Constants.Constants;
import com.example.fireo.activities.ProfileSettingsActivity;
import com.example.fireo.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

public class ProfileSettingsPresenter {
    private ProfileSettingsActivity activity;
    private BaseApplication application;

    public ProfileSettingsPresenter(ProfileSettingsActivity profileSettingsActivity) {
        this.activity = profileSettingsActivity;
        application = activity;
        profileSettingsActivity.init();
    }

    private void saveInformation(String email, String phoneNumber) {
        User user = BaseApplication.user;
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);

        application.getFireStore().collection(Constants.Collections.USER).document(BaseApplication.user.getUserId()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(activity, "Successfully updated ", Toast.LENGTH_SHORT).show();
                    BaseApplication.user = user;
                } else {
                    Toast.makeText(activity, "Try again later", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void updateFirebaseUser(String email, String phoneNumber) {
        FirebaseUser newFirebaseUser = BaseApplication.getFirebaseUser();
        newFirebaseUser.updateEmail(email);
        BaseApplication.getFirebaseAuth().getCurrentUser().updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                saveInformation(email, phoneNumber);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface View {
        void init();

        void setData();
    }
}
