package com.zwt.zwttransmit.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {
    public static String getDate(long time){
        return new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault())
                .format(new Date(time*1000L));
    }

    public static String getDateMinute(long time) {
        return new SimpleDateFormat("yyyy/MM/dd a hh:mm:ss", Locale.getDefault())
                .format(new Date(time*1000L));
    }

}
