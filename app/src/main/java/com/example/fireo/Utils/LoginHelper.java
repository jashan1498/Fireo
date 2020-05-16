package com.example.fireo.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginHelper {

    public static boolean validateEmail(String email) {
        String regex = "[a-z|0-9|_%+-]+@[a-z|0-9]+.[a-z|0-9]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
