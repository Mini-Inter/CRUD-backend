package com.school.miniinter.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailUtils {
    public static boolean verifyEmail(String email) {
        Pattern vidyaEmail = Pattern.compile("^[a-z0-9._%+-]+\\.[a-z0-9._%+-]+@vidya\\.org\\.br$");
        Matcher match = vidyaEmail.matcher(email);
        return match.find();
    }
}
