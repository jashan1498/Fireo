package com.example.fireo.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class LoginUtils {

    private static final String CURRENT_USER_NAME = "LoginUsername";
    private Context context;
    private LoginListener loginListener;
    private String user;

    public LoginUtils(Context context) {
        this.context = context;
    }

    public String getUsername() {
        user = getUserFromCache();
        return user;
    }

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    public void setLoginState(String user, boolean isLoggedIn) {
        try {
            this.user = user;
            saveState();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        if (loginListener != null) {
            loginListener.onLoginChanged(user, isLoggedIn);
        }
    }

    private void saveState() {
        SharedPreferences.Editor editor = context.getSharedPreferences(CURRENT_USER_NAME,
                MODE_PRIVATE).edit();
        editor.putString(CURRENT_USER_NAME, user);
        editor.apply();
    }

    private String getUserFromCache() {
        SharedPreferences preferences = context.getSharedPreferences(CURRENT_USER_NAME,
                MODE_PRIVATE);
        user = preferences.getString(CURRENT_USER_NAME, null);
        return user;
    }

}
