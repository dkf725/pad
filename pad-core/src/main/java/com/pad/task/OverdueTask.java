package com.pad.task;

import com.pad.service.PeriodizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 逾期定时任务
 */
@Component
public class OverdueTask {

    @Autowired
    private PeriodizationService periodizationService;

    /*秒 分 时 日 月 周 年
    * 每天凌晨一点判断是否逾期*/
    @Scheduled(cron = " 0 0 1 * * ?")
   /* @Scheduled(cron = " 0 57 22 * * ?")*/
    public void task1(){
        periodizationService.isOverdue();
        System.out.println("定时任务执行");
    }
}
