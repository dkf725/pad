package com.pad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pad.entity.ApprovalRecord;
import com.pad.mapper.ApprovalRecordMapper;
import com.pad.service.ApprovalRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 审批记录表 服务实现类
 * </p>
 *
 * @author F4
 * @since 2022-09-02
 */
@Service
public class ApprovalRecordServiceImpl extends ServiceImpl<ApprovalRecordMapper, ApprovalRecord> implements ApprovalRecordService {

    @Override
    public void pageQuery(Page<ApprovalRecord> Page, ApprovalRecord approvalRecord) {
        //构造条件
        LambdaQueryWrapper<ApprovalRecord> wrapper = new LambdaQueryWrapper<>();
        //判断条件是否为空
        if (ObjectUtils.isEmpty(approvalRecord)){
            //条件为空 直接分页查询
            baseMapper.selectPage(Page,null);
            return;
        }
        //查询
        baseMapper.selectPage(Page,wrapper);
    }
}
