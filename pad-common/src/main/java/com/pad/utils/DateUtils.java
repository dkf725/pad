package com.pad.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
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
     * 判断是否逾期
     * @param nowDate 当前时间
     * @param originallyTime 时间还款时间
     * @return true 逾期
     */
    public static boolean isOverdue(Date nowDate,Date originallyTime){
        Calendar now = Calendar.getInstance();
        now.setTime(nowDate);

        Calendar origin = Calendar.getInstance();
        origin.setTime(originallyTime);

        if (now.after(origin)){
            //当前时间是否在原定还款时间后 如果超过则返回true 逾期
            return true;
        }else {
            //未超过 返回false 未逾期
            return false;
        }
    }

}
