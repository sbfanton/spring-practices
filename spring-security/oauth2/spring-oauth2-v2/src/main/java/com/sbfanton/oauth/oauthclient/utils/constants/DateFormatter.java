package com.sbfanton.oauth.oauthclient.utils.constants;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DateFormatter {

    public static final String BASIC_FORMATTER = "yyyy-MM-dd HH:mm:ss";

    public static String basicFormatter(String format) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(timestamp);
    }
}
