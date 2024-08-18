package com.hdq.identity_service.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;


@Slf4j
public class StringUtil {

    public static final String ALPHA = "abcdefghijklmnopqrstuvwxyz"; // a-z
    public static final String ALPHA_UPPER_CASE = ALPHA.toUpperCase(); // A-Z
    public static final String DIGITS = "0123456789"; // 0-9
    public static final String SPECIALS = "~=+%^*/()[]{}/!@#$?|";
    public static final String ALPHA_NUMERIC = ALPHA + ALPHA_UPPER_CASE + DIGITS;
    public static final String ALL = ALPHA + ALPHA_UPPER_CASE + DIGITS + SPECIALS;
    public static final String UNDERSCORE = "_";
    public static final String ALPHA_UPPER_DIGITS_UNDERSCORE = ALPHA_UPPER_CASE + DIGITS + UNDERSCORE;


    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static boolean isNotEmpty(String string) {
        return string != null && !string.isEmpty();
    }

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static String transform(Object object) {
        if (object == null) {
            return "NULL";
        }
        return object.toString();
    }

    public static String randomString(int length, String characters) {
        StringBuilder str = new StringBuilder(length);
        int lengthCharacters = characters.length();
        for (int i = 0; i < length; i++) {
            int randIndex = (int) (Math.random() * lengthCharacters);
            str.append(characters.charAt(randIndex));
        }
        return str.toString();
    }

    public static String randomStringWithAlphaUpperDigitAndUnderScore(int length) {
        Random random = new Random();
        StringBuilder str = new StringBuilder(length);
        int lengthCharacters = ALPHA_UPPER_DIGITS_UNDERSCORE.length();
        str.append(ALPHA_UPPER_CASE.charAt(random.nextInt(ALPHA_UPPER_CASE.length())));

        for (int i = 1; i < length - 1; i++) {
            str.append(ALPHA_UPPER_DIGITS_UNDERSCORE.charAt(random.nextInt(lengthCharacters)));
        }
        char lastChar;
        do {
            lastChar = ALPHA_UPPER_DIGITS_UNDERSCORE.charAt(random.nextInt(lengthCharacters));
        } while (lastChar == '_');
        str.append(lastChar);

        return str.toString();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("^0\\d{9}$");
    }
}
