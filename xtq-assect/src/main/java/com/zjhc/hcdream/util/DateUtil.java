package com.zjhc.hcdream.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Desc:
 * email: zhangqj@zjhcsoft.com
 * Created by ZhangQingJing on 2016/1/8 0:19
 */
public class DateUtil {
    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static SimpleDateFormat format=new SimpleDateFormat(FORMAT);
    public static String newDateStr(){
        return format.format(new Date());
    }
}
