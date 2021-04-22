package com.zwt.zwttransmit.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    public static String getDateAdvanced(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        Date now = new Date();
        String nowStr = sdf.format(now);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_YEAR,-1);//日期减一天
        Date yesterday = calendar.getTime();
        String yesterdayStr = sdf.format(yesterday);

        String lastYear = nowStr.substring(0, 4);

        String timeStr = sdf.format(new Date(time*1000L));
        if (nowStr.equals(timeStr)) {  //今天
            return "今天";
        }else if (yesterdayStr.equals(timeStr)) {     //昨天
            return "昨天 ";
        }else {
            if (timeStr.startsWith(lastYear)){
                return new SimpleDateFormat("M月d日", Locale.getDefault())
                        .format(new Date(time*1000L));
            }else {
                return new SimpleDateFormat("yyyy年M月d日", Locale.getDefault())
                        .format(new Date(time*1000L));
            }
        }
    }
}
