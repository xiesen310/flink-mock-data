package top.xiesen.mock.kafka.utils;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+08:00");

    private static ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy.MM.dd");
        }
    };
    private static ThreadLocal<SimpleDateFormat> utcSdf = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        }
    };

    public static Long timestamp(String timestamp) {
        return new DateTime(timestamp).toDate().getTime();
    }

    public static String format(String timestamp) throws ParseException {
        return sdf.get().format(new DateTime(timestamp).toDate());
    }

    public static Long utcDate2Timestamp(String utcDateStr) throws ParseException {
        return utcSdf.get().parse(utcDateStr).getTime();
    }

    public static String getUTCTimeStr() {
        return format.format(new Date()).toString();
    }

    public static String getUTCTimeStr(long interval) {
        long currentTimeMillis = System.currentTimeMillis();
        return format.format(new Date(currentTimeMillis + interval)).toString();
    }

    public static void main(String[] args) {
//        System.out.println(getUTCTimeStr());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());
        System.out.println(today);
    }
}
