package com.example.fireo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fireo.presenter.AccountPresenter;

public class AccountFragment extends Fragment implements AccountPresenter.AccountView {
    static final String TAG = "ACCOUNT_FRAGMENT";
    private View view;
    private AccountPresenter presenter;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.account_view, container, false);
        presenter = new AccountPresenter(this);
        if (view != null) {
            context = view.getContext();
        }
        return view;
    }

    @Override
    public void init() {
        LinearLayout profileSettingsButton = view.findViewById(R.id.profile_settings);

        profileSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileSettingsActivity.class);
                startActivity(intent);
            }
        });

    }
}
