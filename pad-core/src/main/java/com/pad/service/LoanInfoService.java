package com.pad.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pad.entity.LoanInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 贷款信息表 服务类
 * </p>
 *
 * @author F4
 * @since 2022-09-02
 */
public interface LoanInfoService extends IService<LoanInfo> {

    //贷款信息表分页显示
    void pageQuery(Page<LoanInfo> page, LoanInfo LoanInfo);

    //逻辑删除用户
    void deleteLoanInfoByIds(List<String> ids);

    //按贷款编号查询每个贷款信息
    LoanInfo findById(String id);

}
