package com.pad.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 逾期信息表
 * </p>
 *
 * @author F4
 * @since 2022-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Overdue对象", description="逾期信息表")
public class Overdue implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "逾期编号")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "还款编号")
    private String rId;

    @ApiModelProperty(value = "逾期利率")
    private Double overdueRate;

    @ApiModelProperty(value = "逾期金额")
    private Double money;

    @ApiModelProperty(value = "逾期开始时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "逾期结束时间")
    private Date endTime;

}
