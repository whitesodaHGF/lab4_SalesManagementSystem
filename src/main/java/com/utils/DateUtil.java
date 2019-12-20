package com.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转换工具类
 *
 * @author:HGF
 */
@Slf4j
public class DateUtil {
//    private static DateFormat format = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ss.0000Z");
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
    /**
     * 时间格式转化
     */
    public static String getDateString(Date date) {
        return format.format(date);
    }

    public static Date getDate(String time) throws ParseException {
        log.info(time);
        time = time.replace("Z", " UTC");
        return format.parse(time);
    }
}
