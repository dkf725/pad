package com.pad.controller;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pad.entity.ApprovalRecord;
import com.pad.entity.CompanyInfo;
import com.pad.entity.LoanInfo;
import com.pad.response.R;
import com.pad.service.ApprovalRecordService;
import com.pad.service.LoanInfoService;
import com.pad.entity.LoanInfo;
import com.pad.service.WebSocket;
import com.pad.utils.MD5;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 * 贷款信息表 前端控制器
 * </p>
 *
 * @author F4
 * @since 2022-09-02
 */
@Api(tags = "贷款管理")
@RestController
@RequestMapping("/loan-info")
public class LoanInfoController {

    @Autowired
    private LoanInfoService loanInfoService;

    @Autowired
    private ApprovalRecordService approvalRecordService;

    @Autowired
    private WebSocket webSocket;

    @PreAuthorize("@me.hasAuthority('company:loanInfo:list')")
    @ApiOperation("贷款信息表分页显示")
    @PostMapping("/list/{current}/{limit}")
    public R loanInfoListPage(
            @ApiParam(name = "current",value = "当前页",required = true)
            @PathVariable long current,
            @ApiParam(name = "limit",value = "每页记录数",required = true)
            @PathVariable long limit,
            @ApiParam(name = "companyInfo",value = "查询条件",required = false)
            @RequestBody(required = false) LoanInfo LoanInfo
    ){
        //创建page对象
        Page<LoanInfo> page = new Page<>(current, limit);
        //查询条件封装在service中
        loanInfoService.pageQuery(page,LoanInfo);
        //获取分页后的列表和总记录数
        List<LoanInfo> loanInfoList = page.getRecords();
        long total = page.getTotal();
        return R.ok().data("total",total).data("loanInfoList",loanInfoList);
    }

    @PreAuthorize("@me.hasAuthority('company:loanInfo:remove')")
    @ApiOperation("根据编号删除贷款表信息")
    @DeleteMapping("/{id}")
    public R deleteLoanInfoByIds(
            @ApiParam(name = "id",value = "要删除的企业用户编号",required = true)
            @PathVariable String[] id
    ){
        List<String> asList = Arrays.asList(id);
        //逻辑删除贷款表信息
        loanInfoService.deleteLoanInfoByIds(asList);
        return R.ok().message("删除成功");
    }

    @PreAuthorize("@me.hasAuthority('company:loanInfo:query')")
    @ApiOperation("按主键查询每个贷款信息")
    @GetMapping("/findBy/{id}")
    public R findBy(@PathVariable("id") String id){
        return R.ok().data("id",loanInfoService.findById(id));
    }

    @PreAuthorize("@me.hasAuthority('company:loanInfo:modify')")
    @ApiOperation("审核贷款")
    @GetMapping("/changeStatus")
    public R changeStatus(
            @ApiParam(name = "id",value = "贷款编号",required = true)
            String id,
            @ApiParam(name = "status",value = "审核状态",required = true)
            Integer status,
            @ApiParam(name = "message",value = "审核说明",required = true)
            String message,
            @ApiParam(name = "character",value = "审核人",required = true)
            Integer type){
        LoanInfo loanInfo = new LoanInfo();
        loanInfo.setId(id);
        loanInfo.setStatus(status);
        loanInfoService.updateById(loanInfo);

        //添加审批记录
        ApprovalRecord approvalRecord = new ApprovalRecord();
        approvalRecord.setLId(id);
        approvalRecord.setStatus(status);
        approvalRecord.setType(type);
        if (StringUtils.hasText(message)){
            approvalRecord.setMessage(message);
        }
        approvalRecordService.save(approvalRecord);
        webSocket.sendMessage("贷款审核有了新的进度");
        return R.ok().message("修改成功");
    }

    @PreAuthorize("@me.hasAuthority('company:loanInfo:edit')")
    @ApiOperation("修改贷款信息")
    @PutMapping("/edit")
    public R editLoanInfo(
            @ApiParam(name = "loanInfo",value = "要修改的贷款信息",required = true)
            @RequestBody LoanInfo loanInfo
    ){
        //更新
        loanInfoService.updateById(loanInfo);
        return R.ok().message("修改贷款信息成功");
    }

    @PreAuthorize("@me.hasAuthority('company:loanInfo:add')")
    @ApiOperation("添加贷款信息")
    @PostMapping("/add")
    public R addLoanInfo(
            @ApiParam(name = "loanInfo",value = "添加的贷款信息",required = true)
            @RequestBody LoanInfo loanInfo
    ){
        //添加
        loanInfoService.save(loanInfo);
        return R.ok().message("添加成功");
    }

    //@PreAuthorize("@me.hasAuthority('company:loanInfo:purpose')")
    @ApiOperation("贷款用途")
    @GetMapping("/loanPurpose")
    public R loanPurpose(){
        LambdaQueryWrapper<LoanInfo> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(LoanInfo::getPurpose,1);
        int count1 = loanInfoService.count(wrapper1);

        LambdaQueryWrapper<LoanInfo> wrapper2 = new LambdaQueryWrapper<>();
        wrapper2.eq(LoanInfo::getPurpose,2);
        int count2 = loanInfoService.count(wrapper2);

        LambdaQueryWrapper<LoanInfo> wrapper3 = new LambdaQueryWrapper<>();
        wrapper3.eq(LoanInfo::getPurpose,3);
        int count3 = loanInfoService.count(wrapper3);

        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("name","个人消费贷款");
        map1.put("value",count1);

        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("name","经营贷款");
        map2.put("value",count2);

        HashMap<String, Object> map3 = new HashMap<>();
        map3.put("name","按揭贷款");
        map3.put("value",count3);

        return R.ok().data("loanPurpose", CollUtil.newArrayList(map1,map2,map3));
    }
}

