package com.vip.file.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @create 2022/5/3 15:33
 * @desc
 */
public class TimeUtils {

    /**
     * 当前时间往前的天数
     * @param n
     * @return
     */
    public static List<String> getCurrentDayAgo( int n ){
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calc =Calendar.getInstance();
        List<String> daysAgo = new ArrayList<>();
        Date now = new Date();
        calc.setTime( now );
        for (int i = 0; i < n; i++) {
            Date minDate = calc.getTime();
            String dayAgo = sdf.format(minDate);
            daysAgo.add( dayAgo );
            calc.add(calc.DATE, -1 );
        }
        return daysAgo;
    }

//    public static void main(String[] args) {
//        TimeUtils.getCurrentDayAgo( 30 );
//    }


}
