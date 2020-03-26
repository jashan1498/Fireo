package com.example.fireo.activities;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.example.fireo.BaseApplication;
import com.example.fireo.R;
import com.example.fireo.databinding.ActivityProfileSettingsBinding;
import com.example.fireo.presenter.ProfileSettingsPresenter;

public class ProfileSettingsActivity extends BaseApplication implements ProfileSettingsPresenter.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

        ActivityProfileSettingsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_settings);
        if (user != null) {
            binding.setUser(user);
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void setData() {

    }
}
