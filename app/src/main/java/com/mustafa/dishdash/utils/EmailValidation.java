package com.mustafa.dishdash.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidation {
    public static boolean isValidEmail(String email) {
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
