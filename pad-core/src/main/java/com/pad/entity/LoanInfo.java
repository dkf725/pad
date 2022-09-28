package com.pad.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 贷款信息表
 * </p>
 *
 * @author F4
 * @since 2022-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="LoanInfo对象", description="贷款信息表")
public class LoanInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "贷款信息编号")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "企业编号")
    private String cNo;

    @ApiModelProperty(value = "银行编号")
    private String bankNo;

    @ApiModelProperty(value = "收款账户")
    private String bankNumber;

    @ApiModelProperty(value = "开户行")
    private String bankType;

    @ApiModelProperty(value = "贷款金额")
    private Double amount;

    @ApiModelProperty(value = "贷款用途")
    private Integer purpose;

    @ApiModelProperty(value = "借款期限")
    private Integer period;

    @ApiModelProperty(value = "还款方式 1等额本息，2等额本金，3每月还息，4一次性还")
    private Integer returnMethod;

    @ApiModelProperty(value = "状态 0未审核，1审核通过，-1审核失败")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(locale = "zh",timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(locale = "zh",timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除 0已删除，1未删除")
    private Integer isDeleted;

    @TableField(exist = false)
    @ApiModelProperty(value = "企业名称")
    private String name;

    @TableField(exist = false)
    @ApiModelProperty(value = "银行名称")
    private String bankName;

    @TableField(exist = false)
    @ApiModelProperty(value = "积分数值")
    private Integer value;

    @TableField(exist = false)
    @ApiModelProperty(value = "企业电话")
    private String phone;

}
