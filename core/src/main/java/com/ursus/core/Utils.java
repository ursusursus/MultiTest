package com.ursus.core;

import android.content.Context;
import android.os.Build;
import android.text.format.DateUtils;
import android.util.Patterns;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by Vlastimil Brečka (www.vlastimilbrecka.sk)
 * on 06-May-17.
 */
public class Utils {

    private static final String GP_URL_PREFIX = "https://play.google.com/store/apps/details?id=";
    private static final String TITLE_NUMBER_PREFIX_REGEX = "(\\d+\\s*(-|–)*\\s*)(\\w.+)";
    private static Pattern sAudioChapterNumberPrefixPattern;
    private static final NumberFormat RATING_FORMATTER = createRatingFormatter();
    private static final NumberFormat DOWNLOADS_COUNT_FORMATTER = NumberFormat.getNumberInstance(Locale.getDefault());

    private static NumberFormat createRatingFormatter() {
        NumberFormat f = NumberFormat.getNumberInstance(Locale.getDefault());
        f.setMinimumFractionDigits(1);
        f.setMaximumFractionDigits(1);
        return f;
    }


    public static boolean hasOreo() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean hasMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static boolean hasNougat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    public static String getGooglePlayUrl(Context context) {
        return getGooglePlayUrl(context.getPackageName());
    }

    public static String getGooglePlayUrl(String packageName) {
        return GP_URL_PREFIX + packageName;
    }

    public static boolean isValidEmailAddress(String input) {
        return Patterns.EMAIL_ADDRESS.matcher(input).matches();
    }

    public static boolean isValidPhoneNumber(String input) {
        return Patterns.PHONE.matcher(input).matches();
    }

    public static String parseTimestamp(long timestamp) {
        return parseTimestamp(timestamp, DateUtils.MINUTE_IN_MILLIS);
    }

    public static String parseTimestamp(long timestamp, long minResolution) {
        return DateUtils.getRelativeTimeSpanString(
                timestamp,
                System.currentTimeMillis(),
                minResolution
        ).toString();
    }
}