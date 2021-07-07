package com.dreamus.lolpark.purchase.constants;

import java.time.format.DateTimeFormatter;

public final class AppConstants {

    // date format
    public static final String DATE_FORMAT_STR = "yyyy-MM-dd";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT_STR);

    // datetime format
    public static final String DATE_TIME_FORMATTER_STR = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER_STR);

    // time format
    public static final String TIME_FORMATTER_STR = "HH:mm:ss";
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMATTER_STR);
}
