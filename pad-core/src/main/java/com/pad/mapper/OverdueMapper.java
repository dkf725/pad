package com.pad.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pad.entity.LoanInfo;
import com.pad.entity.Overdue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 逾期信息表 Mapper 接口
 * </p>
 *
 * @author F4
 * @since 2022-09-02
 */
public interface OverdueMapper extends BaseMapper<Overdue> {

    //逾期信息表分页显示
    IPage<Overdue> pageQuery(IPage<Overdue> page, Overdue overdue);
}
