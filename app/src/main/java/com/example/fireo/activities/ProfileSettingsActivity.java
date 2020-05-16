package com.example.fireo.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.databinding.DataBindingUtil;

import com.example.fireo.BaseApplication;
import com.example.fireo.R;
import com.example.fireo.Utils.LoginHelper;
import com.example.fireo.databinding.ActivityProfileSettingsBinding;
import com.example.fireo.presenter.ProfileSettingsPresenter;

public class ProfileSettingsActivity extends BaseApplication implements ProfileSettingsPresenter.View {
    ProfileSettingsPresenter presenter;
    EditText emailText, phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

        ActivityProfileSettingsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_settings);
        if (user != null) {
            binding.setUser(user);
        }
        presenter = new ProfileSettingsPresenter(this);
    }

    @Override
    public void init() {
        emailText = findViewById(R.id.email_edit_text);
        phoneNumber = findViewById(R.id.phone_number_edit_text);
        findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }

    private void validate() {
        if (emailText.getText().length() >= 5 && phoneNumber.getText().length() >= 6 && LoginHelper.validateEmail(emailText.getText().toString())) {
            presenter.updateFirebaseUser(emailText.getText().toString(), phoneNumber.getText().toString());
        }
    }


    @Override
    public void setData() {

    }
}
