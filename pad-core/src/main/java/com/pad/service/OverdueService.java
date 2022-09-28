package com.pad.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pad.entity.CompanyInfo;
import com.pad.entity.Overdue;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 逾期信息表 服务类
 * </p>
 *
 * @author F4
 * @since 2022-09-02
 */
public interface OverdueService extends IService<Overdue> {

    //逾期信息表分页显示
    IPage<Overdue> pageQuery(IPage<Overdue> page, Overdue overdue);

}
