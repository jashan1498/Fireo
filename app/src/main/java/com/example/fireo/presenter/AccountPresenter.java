package com.example.fireo.presenter;

import com.example.fireo.fragments.AccountFragment;

public class AccountPresenter {
    AccountFragment activity;

    public AccountPresenter(AccountFragment activity) {
        this.activity = activity;
        activity.init();
    }

    public interface AccountView {
        void init();
    }

}
