package com.jls.common;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by jls on 03/03/17.
 */
public class DateUtils {

    public static String  toIsoDate(ZonedDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        return formatter.format(date);
    }
}
