package com.binarycase.saudiassociation.loginRegister.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ValidateUtils {

    public static boolean validPhone(String phone){
        Pattern pattern = Pattern.compile("^[1-9]{1}[0-9]{8}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.find();
    }

    public static boolean validPrice(String phone){
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(phone);
        return matcher.find();
    }
    public static boolean validPassword(String password) {
        return password.length() >= 6;
    }


    public static boolean missingInputs(String... inputs) {
        for (String input : inputs) {
            if (input.isEmpty())
                return true;
        }
        return false;
    }

    public static boolean validMail(String email) {
        return email.matches("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    }

}
