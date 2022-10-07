package com.pad.service;

import com.pad.entity.Periodization;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 分期还款表 服务类
 * </p>
 *
 * @author F4
 * @since 2022-09-02
 */
public interface PeriodizationService extends IService<Periodization> {
    //添加逾期信息
    void isOverdue();
}
