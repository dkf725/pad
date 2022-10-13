package com.pad.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pad.entity.CompanyInfo;
import com.pad.entity.Overdue;
import com.pad.entity.Periodization;
import com.pad.response.R;
import com.pad.service.OverdueService;
import com.pad.service.PeriodizationService;
import com.pad.service.WebSocket;
import com.pad.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    @Autowired
    private PeriodizationService periodizationService;

    @Autowired
    private WebSocket webSocket;

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

    /**
     * 判断是否逾期
     * 逾期则向逾期表添加数据
     * 向客户返回信息 (指定用户)
     * 计算逾期金额
     * 将分期状态改为逾期
     */
    @ApiOperation("添加逾期信息")
    @GetMapping("/add")
    public R isOverdue(){
        periodizationService.isOverdue();
        return R.ok().message("逾期检查完成");
    }
}

