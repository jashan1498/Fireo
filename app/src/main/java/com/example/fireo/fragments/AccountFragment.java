package com.example.fireo.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.fireo.BaseApplication;
import com.example.fireo.R;
import com.example.fireo.Utils.DialogHelper;
import com.example.fireo.Utils.SharedPrefUtils;
import com.example.fireo.activities.AuthorisedBuildingsActivity;
import com.example.fireo.activities.MainActivity;
import com.example.fireo.activities.ProfileSettingsActivity;
import com.example.fireo.presenter.AccountPresenter;

public class AccountFragment extends Fragment implements AccountPresenter.AccountView {
    public static final String TAG = "ACCOUNT_FRAGMENT";
    private Switch themeSwitch;
    private View view;
    private AccountPresenter presenter;
    private Context context;
    LinearLayout authorisedBuildings;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.account_view, container, false);

        themeSwitch = view.findViewById(R.id.themeSwitch);
        authorisedBuildings = view.findViewById(R.id.authorised_buildings);
        presenter = new AccountPresenter(this);
        if (view != null) {
            context = view.getContext();
        }
        return view;
    }

    @Override
    public void init() {
        SharedPrefUtils utils = new SharedPrefUtils(getActivity());
        LinearLayout profileSettingsButton = view.findViewById(R.id.profile_settings);
        CardView logoutButton = view.findViewById(R.id.logout);
        TextView authority = view.findViewById(R.id.authority);
        TextView userName = view.findViewById(R.id.fullName);
        if (BaseApplication.user != null) {
            authority.setText(getResources().getString(R.string.authority_text).concat(BaseApplication.user.getType()));
            userName.setText(BaseApplication.user.getFullName());
        }



        authorisedBuildings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthorisedBuildingsActivity.redirect(context);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogHelper dialogHelper = new DialogHelper(getActivity());
                dialogHelper.createYesNoDialog(true, getActivity().getString(R.string.logout_content), getActivity().getString(R.string.logout));
                dialogHelper.setDialogListener(new DialogHelper.DialogListener() {
                    @Override
                    public void onClick(boolean accepted) {
                        if (accepted) {
                            BaseApplication application = (BaseApplication) getActivity();
                            if (application != null) {
                                application.logout();
                            }
                        } else {
                            dialogHelper.dismissDialog();
                        }
                    }
                });
            }
        });

        profileSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileSettingsActivity.class);
                startActivity(intent);
            }
        });
        themeSwitch.setChecked(utils.getDayNightMode());
        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // dark mode enabled
                    utils.setDayNightMode(true);
                } else {
                    // dark mode disabled
                    utils.setDayNightMode(false);
                }
                applyTheme();
            }
        });

    }

    private void applyTheme() {
        // finish() and start app again
        getActivity().finish();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
}
