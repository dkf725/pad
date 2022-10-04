package com.pad.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 分期还款表
 * </p>
 *
 * @author F4
 * @since 2022-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Periodization对象", description="分期还款表")
public class Periodization implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分期编号")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "贷款信息编号")
    private String lId;

    @ApiModelProperty(value = "本金（借多少）月供本金")
    private String capital;

    @ApiModelProperty(value = "利息 月供利息")
    private String interest;

    @ApiModelProperty(value = "本息 月供")
    private String ci;

    @ApiModelProperty(value = "还款金额")
    private String money;

    @ApiModelProperty(value = "实际还款时间")
    private Date createTime;

    @ApiModelProperty(value = "是否逾期 0是 1否")
    private Integer overdue;

    @ApiModelProperty(value = "还款卡号")
    private String number;

    @ApiModelProperty(value = "还款状态  0待还款 1已还款 2逾期")
    private Integer status;

    @ApiModelProperty(value = "原定还款时间")
    private Date originallyTime;

    @ApiModelProperty(value = "期数")
    private Integer periods;


}
