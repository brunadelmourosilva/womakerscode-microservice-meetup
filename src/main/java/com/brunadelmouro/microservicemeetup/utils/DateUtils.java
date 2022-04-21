package com.brunadelmouro.microservicemeetup.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String convertSystemTimeMillisToString(Long systemMillis){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date resultDate = new Date(systemMillis);
        return sdf.format(resultDate);
    }
}

