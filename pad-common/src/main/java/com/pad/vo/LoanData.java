package com.pad.vo;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanData implements Serializable {
    private double principal;  //贷款金额

    private int term; //贷款总期数

    private double rate; //年利率

    private double totalMoney;  //还款金额（本金+利息）

    private double totalInterests; //利息总额

    private double detail[][];  //每月还款详情

    @ApiParam(name = "type" ,value = "还款方式")
    private Integer type;
    /*  1等额本息，2等额本金*/

}
