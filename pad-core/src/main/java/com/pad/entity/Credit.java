package com.pad.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 放款表
 * </p>
 *
 * @author F4
 * @since 2022-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Credit对象", description="放款表")
public class Credit implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "放款编号")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @JsonProperty(value = "lId")
    @ApiModelProperty(value = "贷款信息编号")
    private String lId;

    @ApiModelProperty(value = "放款类型 0现金，1汇款")
    private Integer type;

    @ApiModelProperty(value = "实际放款金额")
    private Double money;

    @ApiModelProperty(value = "平台所收手续费")
    private Double service;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "逻辑删除 0已删除，1未删除")
    private Integer isDeleted;

    @TableField(exist = false)
    @ApiModelProperty(value = "企业名称")
    private String name;

    @TableField(exist = false)
    @ApiModelProperty(value = "还款方式 1等额本息，2等额本金，3每月还息，4一次性还")
    private Integer returnMethod;

}
