package com.beyondsoft.rdc.cloud.iot.iam.common.util;

import com.google.common.collect.Lists;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {

    private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

    public static Date getNowDate(){
        Calendar cal=Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);//0点
        cal.set(Calendar.MINUTE, 0);//0分
        cal.set(Calendar.SECOND, 0);//0秒
        cal.set(Calendar.MILLISECOND, 0);//0毫秒
        return cal.getTime();
    }

    /**
     * 获取连个日期之间相差的月份
     * @param startDate
     * @param endDate
     * @return
     * @throws ParseException
     */
    public static List getDayList(Date startDate, Date endDate) {
        // 返回的日期集合
        List<String> resultList = Lists.newArrayList();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(startDate);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(endDate);
        tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
        while (tempStart.before(tempEnd)) {
            resultList.add(sdf.format(tempStart.getTime()));
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        return resultList;
    }

    /**
     *
     * @param nowTime   当前时间
     * @param startTime	开始时间
     * @param endTime   结束时间
     * @return
     * @author sunran   判断当前时间在时间区间内
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 天24小时
     * @return
     */
    public static List getDay24(){
        List list = Lists.newArrayList();
        list.addAll(Arrays.asList(new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "00"}));
        return list;
    }

    /**
     * 查询指定日期前后指定的天数
     * @param date 日期对象
     * @param days 天数
     * @return 日期对象
     */
    public static Date incr(Date date, int days)
    {
        if (date == null){
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

    public static String dateToStr(Date date){
        return sdf.format(date);
    }

    /*public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Date> dateList = DateUtil.getDayList(sdf.parse("2020-07-24 10:15:23"), sdf.parse("2020-07-30 10:15:23"));
        System.out.println(dateList);
    }*/
}
