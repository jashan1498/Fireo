package com.example.fireo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.fireo.databinding.HomeViewBindingImpl;
import com.example.fireo.model.Dashboard;
import com.example.fireo.presenter.HomePresenter;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeFragment extends Fragment implements HomePresenter.HomeView {
    static final String TAG = "HOME_FRAGMENT";
    private LinearLayout tempCard;
    private HomePresenter homePresenter;
    private HomeViewBindingImpl binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_view, container, false);
        tempCard = binding.getRoot().findViewById(R.id.temp_card);

        homePresenter = new HomePresenter(this);
        homePresenter.fetchData();
        return binding.getRoot();
    }

    @Override
    public void init() {
        if (getActivity() != null) {
            FirebaseUser firebaseUser = BaseApplication.getFirebaseUser();
            FirebaseFirestore firestore = ((BaseApplication) getActivity()).getFireStore();

            String uid = firebaseUser.getUid();
            tempCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), DummyDataAdd.class);
                    startActivity(intent);
                }
            });


        }

    }

    @Override
    public void setData(Dashboard dashboard) {
        binding.setDashboard(dashboard);
    }
}
