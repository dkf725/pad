package com.pad.service.impl;

import com.pad.entity.Overdue;
import com.pad.entity.Periodization;
import com.pad.mapper.OverdueMapper;
import com.pad.mapper.PeriodizationMapper;
import com.pad.service.PeriodizationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pad.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 分期还款表 服务实现类
 * </p>
 *
 * @author F4
 * @since 2022-09-02
 */
@Service
public class PeriodizationServiceImpl extends ServiceImpl<PeriodizationMapper, Periodization> implements PeriodizationService {
    @Autowired
    private OverdueMapper overdueMapper;

    //添加逾期信息
    @Override
    public void isOverdue() {
    //查询所有未还款的分期信息 和逾期利率
        List<Periodization> periodizationList = baseMapper.selectOverdueList();
        if (periodizationList.size()>0){
            for (Periodization periodization : periodizationList) {
                Date originallyTime = periodization.getOriginallyTime();
                boolean overdue = DateUtils.isOverdue(new Date(), originallyTime);
                if (overdue){
                    //逾期
                    //计算逾期金额
                    Double ci = periodization.getCi();
                    Double overdueRate = periodization.getOverdueRate();
                    Double overdueMoney = Math.floor(overdueRate / 100 * ci);
                    //向逾期表添加数据
                    Overdue overdueInfo = new Overdue();
                    overdueInfo.setRId(periodization.getId());
                    overdueInfo.setOverdueRate(overdueRate);
                    overdueInfo.setMoney(overdueMoney);
                    overdueMapper.insert(overdueInfo);
                    //将分期状态改为逾期
                    periodization.setStatus(2);
                    periodization.setOverdue(0);
                    periodization.setCi(Math.floor(ci+overdueMoney));
                    periodization.setOverdueMoney(overdueMoney);
                    baseMapper.updateById(periodization);
                }
            }
        }
    }
}
