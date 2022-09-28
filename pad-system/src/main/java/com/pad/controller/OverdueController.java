package com.pad.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pad.entity.CompanyInfo;
import com.pad.entity.Overdue;
import com.pad.response.R;
import com.pad.service.OverdueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 逾期信息表 前端控制器
 * </p>
 *
 * @author F4
 * @since 2022-09-02
 */
@Api(tags = "逾期表管理")
@RestController
@RequestMapping("/overdue")
public class OverdueController {

    @Autowired
    private OverdueService overdueService;

    @PreAuthorize("@me.hasAuthority('overdue:info:list')")
    @ApiOperation("逾期信息表分页显示")
    @PostMapping("/list/{current}/{limit}")
    public R overdueListPage(
            @ApiParam(name = "current",value = "当前页",required = true)
            @PathVariable long current,
            @ApiParam(name = "limit",value = "每页记录数",required = true)
            @PathVariable long limit
            //@ApiParam(name = "companyInfo",value = "查询条件",required = false)
            //@RequestBody(required = false) CompanyInfo companyInfo
    ){
        //创建page对象
        Page<Overdue> page = new Page<Overdue>(current, limit);
        //查询条件封装在service中
        overdueService.pageQuery(page,null);
        //获取分页后的列表和总记录数
        List<Overdue> overdueList = page.getRecords();
        long total = page.getTotal();
        return R.ok().data("total",total).data("overdueList",overdueList);
    }

    @PreAuthorize("@me.hasAuthority('overdue:info:update')")
    @ApiOperation("逾期表，还款，更新时间")
    @PutMapping("/updateTime")
    public R updateTime(
            @ApiParam(name = "overdue",value = "要查询的逾期用户",required = true)
            @RequestBody Overdue overdue
    ){
        //修改逾期表的还款时间
        Overdue overdueInfo = new Overdue();
        overdue.setEndTime(overdueInfo.getEndTime());
        overdueService.updateById(overdue);
        return R.ok().message("还款成功");
    }
}

