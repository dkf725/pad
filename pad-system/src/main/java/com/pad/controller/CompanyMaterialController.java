package com.pad.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.pad.entity.ApprovalRecord;
import com.pad.entity.CompanyInfo;
import com.pad.entity.CompanyMaterial;
import com.pad.response.R;
import com.pad.service.ApprovalRecordService;
import com.pad.service.CompanyMaterialService;
import com.pad.service.WebSocket;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 企业用户材料信息表 前端控制器
 * </p>
 *
 * @author F4
 * @since 2022-09-02
 */
@Api(tags = "企业用户上传材料信息")
@RestController
@RequestMapping("/company-material")
public class CompanyMaterialController {
    @Autowired
    private CompanyMaterialService service;

    @Autowired
    private WebSocket webSocket;

    @Autowired
    private ApprovalRecordService approvalRecordService;

    //修改
    @ApiOperation("材料修改状态")
    @PutMapping("/update")
    public R update(
            @ApiParam(name="companyMaterial" ,value = "材料信息")
            @RequestBody CompanyMaterial companyMaterial){
        boolean a= service.updateById(companyMaterial);
        if (a){
            return R.ok().message("更新成功");
        }
        return R.error().message("更新失败");
    }

    @ApiOperation("企业用户材料查询接口")
    //按外键查询
    @GetMapping("/findMaterialByPK/{id}")
    @ResponseBody
    public R findByPK(@ApiParam(value = "企业用户外键") @PathVariable("id") String cNo){
        System.out.println(service.selectByFK(cNo));
        return R.ok().data("material",service.selectByFK(cNo));
    }


    @ApiOperation("根据编号修改认证状态")
    @GetMapping("/modify")
    public R modifyStatus(
            @ApiParam(name = "cNo",value = "要查询的企业用户编号",required = true)
            String cNo,
            @ApiParam(name = "id",value = "贷款编号",required = true)
            String id,
            @ApiParam(name = "status",value = "审核状态",required = true)
            Integer status,
            @ApiParam(name = "message",value = "审核说明",required = true)
            String message,
            @ApiParam(name = "character",value = "审核人",required = true)
            Integer type
    ){
        //修改认证状态
        System.out.println(cNo);
        service.deleteCompanyMaterialByIds(cNo);

        //添加审批记录
        ApprovalRecord approvalRecord = new ApprovalRecord();
        approvalRecord.setLId(id);
        approvalRecord.setStatus(status);
        approvalRecord.setType(type);
        if (StringUtils.hasText(message)){
            approvalRecord.setMessage(message);
        }
        approvalRecordService.save(approvalRecord);
        webSocket.sendMessage("材料已驳回");
        return R.ok().message("驳回成功");
    }

}

