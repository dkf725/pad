package com.pad.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pad.entity.ApprovalRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 审批记录表 服务类
 * </p>
 *
 * @author F4
 * @since 2022-09-02
 */
public interface ApprovalRecordService extends IService<ApprovalRecord> {

    //审核记录分页显示
    void pageQuery(Page<ApprovalRecord> Page, ApprovalRecord approvalRecord);

}
