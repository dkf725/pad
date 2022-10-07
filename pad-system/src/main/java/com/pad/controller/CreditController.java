package com.pad.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pad.entity.*;
import com.pad.response.R;
import com.pad.service.*;
import com.pad.utils.LoanCalculator;
import com.pad.vo.LoanData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 放款表 前端控制器
 * </p>
 *
 * @author F4
 * @since 2022-09-02
 */
@Api(tags = "放款管理")
@RestController
@RequestMapping("/credit")
public class CreditController {

    @Autowired
    private CreditService creditService;

    @Autowired
    private ProfitService profitService;

    @Autowired
    private LoanInfoService loanInfoService;

    @Autowired
    private BankService bankService;

    @Autowired
    private PeriodizationService periodizationService;

    @Autowired
    private WebSocket webSocket;


    @PreAuthorize("@me.hasAuthority('credit:info:list')")
    @ApiOperation("放款信息表分页显示")
    @PostMapping("/list/{current}/{limit}")
    public R creditListPage(
            @ApiParam(name = "current",value = "当前页",required = true)
            @PathVariable long current,
            @ApiParam(name = "limit",value = "每页记录数",required = true)
            @PathVariable long limit,
            @ApiParam(name = "credit",value = "查询条件",required = false)
            @RequestBody(required = false) Credit credit
    ){
        //创建page对象
        Page<Credit> page = new Page<>(current, limit);
        //查询条件封装在service中
        creditService.pageQuery(page,credit);
        //获取分页后的列表和总记录数
        List<Credit> creditList = page.getRecords();
        long total = page.getTotal();
        return R.ok().data("total",total).data("creditList",creditList);
    }

    @PreAuthorize("@me.hasAuthority('credit:info:add')")
    @ApiOperation("放款")
    @PostMapping("/add")
    public R creditListPage(
            @ApiParam(name = "credit",value = "放款",required = true)
            @RequestBody Credit credit
    ){
        //放款
        creditService.save(credit);
        //放款的同时 计算分期还款时间
        List<Periodization> periodizationList = new ArrayList<>();
        String loanId = credit.getLId();
        LoanInfo loanInfo = loanInfoService.getById(loanId);
        Bank bank = bankService.getById(loanInfo.getBankNo());
        LoanData loanData = new LoanData();
        //贷款金额
        loanData.setPrincipal(loanInfo.getAmount());
        //年利率
        loanData.setRate(bank.getBorrowYearRate());
        //贷款总期数
        Integer term = loanInfo.getPeriod();
        loanData.setTerm(term);
        //还款方式
        Integer type = loanInfo.getReturnMethod();
        //计算数据
        LoanData data = null;
        if (1==type){
            //等额本息还款
            data = LoanCalculator.EqualPrincipalandInterestMethod(loanData);
        }
        if (2==type){
            //等额本金还款
            data = LoanCalculator.EqualPrincipalMethod(loanData);
        }
        //将计算的数据封装到分期还款表
        double[][] detail = data.getDetail();
        //原定还款时间根据放款时间 每月加一
        Calendar ct=Calendar.getInstance();
        Date createTime = credit.getCreateTime();
        ct.setTime(createTime);
        for (int i = 0; i < term; i++) {
            Periodization periodization = new Periodization();
            periodization.setLId(loanId);
            periodization.setCapital(detail[i][0]);
            periodization.setInterest(detail[i][1]);
            periodization.setCi(detail[i][2]);
            periodization.setPeriods(i+1);
            // 原定还款时间根据放款时间 每月加一
            ct.add(Calendar.MONTH, 1);
            periodization.setOriginallyTime(ct.getTime());
            periodizationList.add(periodization);
        }
        //保存到数据库
        periodizationService.saveBatch(periodizationList);
        //放款的同时 添加平台收益信息
        Profit profit = new Profit();
        profit.setCId(credit.getId());
        profit.setValue(credit.getService());
        profitService.save(profit);
        webSocket.sendMessage("已放款");
        return R.ok().message("放款成功");
    }

    @PreAuthorize("@me.hasAuthority('credit:info:query')")
    @ApiOperation("查询是否放款")
    @GetMapping("/query/{id}")
    public R creditQuery(
            @ApiParam(value = "贷款编号",name="id",required = true)
        @PathVariable String id
    ){
        LambdaQueryWrapper<Credit> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Credit::getLId,id);
        int count = creditService.count(wrapper);
        return R.ok().data("count",count);
    }
}

