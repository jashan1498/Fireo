package com.example.fireo.presenter;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.fireo.BaseApplication;
import com.example.fireo.Constants.Constants;
import com.example.fireo.HomeFragment;
import com.example.fireo.R;
import com.example.fireo.model.Dashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

public class HomePresenter {
    private HomeFragment fragment;

    public HomePresenter(HomeFragment fragment) {
        this.fragment = fragment;
        fragment.init();
    }

    public void fetchData() {
        BaseApplication application = (BaseApplication) fragment.getActivity();
        if (application != null) {
            FirebaseUser user = BaseApplication.getFirebaseUser();
            application.getFireStore().collection(Constants.Collections.DASHBOARD).document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        Dashboard dashboard = task.getResult().toObject(Dashboard.class);
                        if (dashboard != null) {
                            fragment.setData(dashboard);
                        }
                    } else {
                        Toast.makeText(fragment.getContext(), fragment.getResources().getString(R.string.error_string), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }


    public interface HomeView {
        void init();

        void setData(Dashboard dashboard);


    }
}
