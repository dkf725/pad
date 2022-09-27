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
 * 企业用户详细信息表
 * </p>
 *
 * @author F4
 * @since 2022-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="CompanyDetail对象", description="企业用户详细信息表")
public class CompanyDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "详情编号")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "企业编号")
    private String cNo;

    @ApiModelProperty(value = "企业类型 0个人独资，1合伙企业，2有限责任公司")
    private Integer type;

    @ApiModelProperty(value = "法人姓名")
    private String legalName;

    @ApiModelProperty(value = "法人证件号")
    private String legalId;

    @ApiModelProperty(value = "注册省")
    private String province;

    @ApiModelProperty(value = "注册市")
    private String city;

    @ApiModelProperty(value = "注册区")
    private String area;

    @ApiModelProperty(value = "企业信誉度")
    private String credit;

    @ApiModelProperty(value = "公司详情地址")
    private String address;

    @ApiModelProperty(value = "营业执照图片")
    private String license;

    @ApiModelProperty(value = "营业执照到期时间")
    @JsonFormat(locale = "zh",timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date licTime;

    @ApiModelProperty(value = "经营范围")
    private String scope;

    @ApiModelProperty(value = "逻辑删除 0已删除，1未删除")
    private Integer isDeleted;

    @TableField(exist = false)
    @ApiModelProperty(value = "企业名称")
    private String name;

    @TableField(exist = false)
    @ApiModelProperty(value = "认证状态   0未认证，1认证中，2认证通过，-1认证失败     默认是0未认证")
    private Integer authStatus;

    @TableField(exist = false)
    @JsonFormat(locale = "zh",timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @TableField(exist = false)
    @ApiModelProperty(value = "注册省")
    private String provinceName;

    @TableField(exist = false)
    @ApiModelProperty(value = "注册市")
    private String cityName;

    @TableField(exist = false)
    @ApiModelProperty(value = "注册区")
    private String areaName;

}
