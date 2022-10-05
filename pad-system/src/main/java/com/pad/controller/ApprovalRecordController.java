package com.pad.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pad.entity.ApprovalRecord;
import com.pad.entity.Message;
import com.pad.response.R;
import com.pad.service.ApprovalRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 审批记录表 前端控制器
 * </p>
 *
 * @author F4
 * @since 2022-09-02
 */
@Api(tags = "审查记录管理")
@RestController
@RequestMapping("/approval-record")
public class ApprovalRecordController {


    @Autowired
    private ApprovalRecordService approvalRecordService;

    @PreAuthorize("@me.hasAuthority('system:message:list')")
    @ApiOperation("审查记录分页显示")
    @PostMapping("/list/{current}/{limit}")
    public R approvalRecordListPage(
            @ApiParam(name = "current",value = "当前页",required = true)
            @PathVariable long current,
            @ApiParam(name = "limit",value = "每页记录数",required = true)
            @PathVariable long limit,
            @ApiParam(name = "approvalRecord",value = "查询条件",required = false)
            @RequestBody(required = false) ApprovalRecord approvalRecord
    ){
        //创建page对象
        Page<ApprovalRecord> Page = new Page<>(current, limit);
        //查询条件封装在service中
        approvalRecordService.pageQuery(Page,approvalRecord);
        //获取分页后的列表和总记录数
        List<ApprovalRecord> approvalRecordList = Page.getRecords();
        long total = Page.getTotal();
        return R.ok().data("total",total).data("approvalRecordList",approvalRecordList);
    }


}

