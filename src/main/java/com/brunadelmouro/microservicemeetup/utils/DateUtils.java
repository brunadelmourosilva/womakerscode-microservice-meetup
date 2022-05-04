package com.brunadelmouro.microservicemeetup.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {
    public static String convertSystemTimeMillisToString(Long systemMillis){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("Brazil/East"));

        Date resultDate = new Date(systemMillis);
        return sdf.format(resultDate);
    }
}

