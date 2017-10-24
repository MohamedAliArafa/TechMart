package com.a700apps.techmart.utils;

import android.widget.EditText;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ahmed agamy on 04/04/2017.
 */

public class Validator {


    public static boolean isEditTextEmpty(EditText editText) {
        return isTextEmpty(editText.getText().toString().trim());
    }


    public static boolean isTextEmpty(String text) {
        return text.equals("");
    }


    public static boolean validEmail(String email) {
        Pattern pattern;
        Matcher matcher;
//        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        final String EMAIL_PATTERN = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean validName(String name) {
        return name.length() < 100;
    }
    public static boolean validNamespecila(String name) {
        final Pattern regex = Pattern.compile("[!&(*^*(^(+:(&(^()(*)(*&^%$#@!#$%^&*()(]");

            if(regex.matcher(name).find())
                return false;
            else if (name.matches(".*\\d+.*"))
                return false;
        return true;

    }


  /*  public static boolean validPassword(String password) {
        // prevent password from accept spaces.
        if (password.contains(" ")) {
            return false;
        }
        // validate password should contain numbers and letters.
        password = password.replaceAll("[!@#$%&*^()_+=|<>?{}\\\\[\\\\]~-]", "");
       // return password.matches("[0-9]+[a-zA-Z]+|[a-zA-Z]+[0-9]+|[a-zA-Z]+[0-9]+[a-zA-Z]+|[0-9]+[a-zA-Z]+[0-9]+");
        return password.matches("^(?=.*\\\\d+)(?=.*[a-zA-Z])[0-9a-zA-Z!@#$%]{8,50}$");
    }*/

    public static boolean validPassword(String password) {
        // prevent password from accept spaces.
        if (!(password.length() < 8) && password.contains(" ")) {
            return false;
        }
        // validate password should contain numbers and letters.
        password = password.replaceAll("[!@#$%&*^()_+=|<>?{}\\\\[\\\\]~-]", "");
        return password.matches("^(?=.*\\d+)(?=.*[a-zA-Z])[0-9a-zA-Z!@#$%]{8,50}$");
    }
    public static boolean validPasswordLength(String password) {
        return !(password.length() < 8|| password.length() > 50);
    }
    //number.length() <= 14 &&
    public static boolean validMobileNumber(String number) {
        number = number.replaceFirst("\\+","");
        return number.length() <= 14 && number.length() >= 11 && number.matches("^[0-9]+$");
    }

    public static boolean validMobileNumberNew(String number) {
//        number = number.replaceFirst("\\+","");
        return number.length() <= 14 && number.length() >= 11 && number.matches("^((\\+))[0-9]{11,14}$");
    }

    public static boolean isMobileNumber(String data) {
        String expr = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        return data.matches(expr);
    }

    public static boolean validNum(String num){
        return  num.matches(".*\\d+.*");
    }

    public static boolean isSameWords(String word1, String word2) {
        return word1.equals(word2);
    }


    public static boolean isAddressInfoValid(String addressInfo) {
        return addressInfo.length() > 8;
    }

    public static boolean validNationalId(String nationId) {
        return nationId.length() >= 8;
    }

    public static boolean validBirthDate(String birthDate) {
        return !birthDate.equals("0000-00-00");
    }
    public static boolean checkSecondDateBiggerEqual(int[] dateFrom, int[] dateTo) {
        Calendar firstCal = Calendar.getInstance();
        firstCal.set(dateFrom[0], dateFrom[1], dateFrom[2], 0, 0, 0);
        Calendar secondCal = Calendar.getInstance();
        secondCal.set(dateTo[0], dateTo[1], dateTo[2], 0, 0, 0);
        return check2DatesArrangeEqual(firstCal, secondCal);
    }


    /**
     * method check if first date is bigger than or equal second date
     *
     * @param
     **/
    public static boolean check2DatesArrangeEqual(Calendar firstCalen, Calendar secondCalen) {
        if (firstCalen.getTimeInMillis() <= secondCalen.getTimeInMillis()) {
            return true;
        } else {
            return false;
        }
    }
}
