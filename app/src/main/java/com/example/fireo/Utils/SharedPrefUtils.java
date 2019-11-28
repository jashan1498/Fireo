package com.example.fireo.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class SharedPrefUtils {

    private static final String APP_LOCALE_FILE_NAME = "APP_LOCALE";
    public static Locale locale;
    private Context context;
    private LoginListener loginListener;
    private LanguageListener languageListener;


    public SharedPrefUtils(Context context) {
        this.context = context;
    }

    public Locale getLocale() {
        locale = getLocaleFromCache();
        return locale;
    }

    public void setLanguageListener(LanguageListener loginListener) {
        this.languageListener = loginListener;
    }

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    public String getLanguage() {
        return locale.getLanguage();
    }

    public String getCountry() {
        return locale.getCountry();
    }

    public String getDisplayCountry() {
        return locale.getDisplayCountry();
    }

    public Boolean setLocale(Locale locale) {
        boolean isChanged = false;
        if (locale != null) {
            SharedPrefUtils.locale = locale;
            isChanged = setLocale();
        }
        return isChanged;
    }

    private boolean setLocale() {
        try {
            Locale local = new Locale(locale.getLanguage(), locale.getCountry());
            Configuration config = context.getResources().getConfiguration();
            Locale.setDefault(local);
            config.locale = local;
            context.getResources().updateConfiguration(config,
                    context.getResources().getDisplayMetrics());
            this.saveLocale();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if (loginListener != null) {
            loginListener.onLoginChanged("",true);
        }
        return true;
    }

    private void saveLocale() {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_LOCALE_FILE_NAME,
                MODE_PRIVATE).edit();
        editor.putString(APP_LOCALE_FILE_NAME, locale.getLanguage());
        editor.apply();
    }

    private Locale getLocaleFromCache() {
        SharedPreferences preferences = context.getSharedPreferences(APP_LOCALE_FILE_NAME,
                MODE_PRIVATE);
        String language = preferences.getString(APP_LOCALE_FILE_NAME, "en");
        return new Locale(language);
    }

    public void setLocaleFromCache(Context context) {
        this.context = context;
        locale = getLocaleFromCache();
        setLocale();
    }
}
