package com.pad.mapper;

import com.pad.entity.Periodization;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 分期还款表 Mapper 接口
 * </p>
 *
 * @author F4
 * @since 2022-09-02
 */
public interface PeriodizationMapper extends BaseMapper<Periodization> {
    //查询所有未还款的分期信息 和逾期利率
    List<Periodization> selectOverdueList();
}
