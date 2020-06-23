package top.xiesen.mock.kafka.test;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeStampTest {
    public static Date StrToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            System.out.print("日期转换异常");
        }
        return date;
    }

    private static DateTimeFormatter dateFormat1 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static void test() {
        String mestime = String.valueOf("20190424092143333");
        String datetime = mestime.substring(0, 15);
        Date date = StrToDate(datetime);
        String times = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date).toString();
        DateTime dateTime2 = DateTime.parse(times, dateFormat1);
        String timestamp = dateTime2.toString();
        System.out.println(timestamp);
    }


    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));

    }

    /**
     * 字符串的日期格式的计算
     */
    public static int daysBetween(String smdate, String bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());
        System.out.println(daysBetween("2018-03-17", today));
    }



}
