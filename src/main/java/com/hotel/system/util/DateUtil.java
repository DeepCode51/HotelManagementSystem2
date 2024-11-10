package com.hotel.system.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author liuyanzhao
 * @date 2022/4/5 4:36 下午
 */

public class DateUtil {

    public static final String FORMAT = "yyyy-MM-dd";
    public static final String FORMAT_CHINESE = "yyyy年MM月dd日";
    public static final ThreadLocal<SimpleDateFormat> THREAD_LOCAL = new ThreadLocal<>();


    public static List<String> getBetweenDates(String start, int count) {
        Date startDate = null;
        SimpleDateFormat sdf = THREAD_LOCAL.get();
        if (sdf == null) {
            sdf = new SimpleDateFormat(FORMAT);
        }
        try {
            startDate = sdf.parse(start);
        } catch (ParseException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.add(Calendar.DAY_OF_MONTH, count);
        Date endDate = c.getTime();
        String end = sdf.format(endDate);
        return getBetweenDates(start, end);
    }

    public static List<String> getBetweenDates(String start, String end) {

        List<String> result = new ArrayList<>();
        try {
            SimpleDateFormat sdf = THREAD_LOCAL.get();
            if (sdf == null) {
                sdf = new SimpleDateFormat(FORMAT);
            }
            Date start_date = sdf.parse(start);
            Date end_date = sdf.parse(end);
            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start_date);
            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end_date);
            while (tempStart.before(tempEnd)) {
                result.add(sdf.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * date2比date1多的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) //同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) //闰年
                {
                    timeDistance += 366;
                } else //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else //不同年
        {
//            System.out.println("判断day2 - day1 : " + (day2 - day1));
            return day2 - day1;
        }
    }

    /*日期加+1天*/
    public static Date dateAddOne(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1); //把日期往后增加一天,整数  往后推,负数往前移动
        date = calendar.getTime(); //这个时间就是日期往后推一天的结果
        return date;

    }


    public static void main(String[] args) {
        System.out.println(getBetweenDates("2020-04-05", 1));
    }

}