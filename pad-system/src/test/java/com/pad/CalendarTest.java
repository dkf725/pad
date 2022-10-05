package com.pad;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarTest {
    @Test
    public void test(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        for (int i = 0; i < 5; i++) {
            calendar.add(Calendar.MONTH,+1);
            String s = sdf.format(calendar.getTime());
            System.out.println(s);
        }
    }
}
