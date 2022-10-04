package com.pad.utils;

import com.pad.vo.LoanData;

public class LoanCalculator {
    //等额本息
    /**
     * 每月还本付息金额 =[ 本金 x 月利率 x(1+月利率)贷款月数 ] / [(1+月利率)还款月数 - 1]
     * 每月利息 = 剩余本金x贷款月利率
     * 还款总利息=贷款额贷款月数月利率*(1+月利率)贷款月数/【(1+月利率)还款月数 - 1】-贷款额
     * 还款总额=还款月数贷款额月利率*(1+月利率)贷款月数/【(1+月利率)还款月数 - 1】
     */
    public static LoanData EqualPrincipalandInterestMethod(LoanData loanData) {
        double principal = loanData.getPrincipal();
        double rate = loanData.getRate();
        int term = loanData.getTerm();

        double totalMoney;
        double totalInterests;
        double detail[][];

        double mIns = rate / 100 / 12; //月利率
        //term 还款所需月份
        double pow = Math.pow(1 + mIns,term);//计算次幂 x的y次方
        double remains = principal;
        totalMoney = (term * principal * mIns * pow) / (pow - 1);  //总还款金额
        totalMoney = Math.floor(totalMoney * 100 + 0.5) / 100;  //floor函数 保留两位小数
        totalInterests = totalMoney - principal;
        totalInterests = Math.floor(totalInterests * 100 + 0.5) / 100;
        double temp[][] = new double[term][3];
        for (int i = 0; i < term; i++) {
            if(i == term - 1) {
                temp[i][1] = remains * mIns;
                temp[i][1] = Math.floor(temp[i][1] * 100 + 0.5) / 100;
                temp[i][0] = remains;
                temp[i][0] = Math.floor(temp[i][0] * 100 + 0.5) / 100;
                temp[i][2] = temp[i][0] + temp[i][1];
                temp[i][2] = Math.floor(temp[i][2] * 100 + 0.5) / 100;
                break;
            }
            //由于精度问题 最后一个月实际的本金会有差别 需要单独计算
            temp[i][1] = remains * mIns;
            temp[i][1] = Math.floor(temp[i][1] * 100 + 0.5) / 100;
            temp[i][2] = totalMoney / term;
            temp[i][2] = Math.floor(temp[i][2] * 100 + 0.5) / 100;
            temp[i][0] = temp[i][2] - temp[i][1];
            temp[i][0] = Math.floor(temp[i][0] * 100 + 0.5) / 100;
            remains -= temp[i][0];
        }
        //temp[][0]为每月还款本金 temp[][1]为每月还款利息 temp[][2]为每月还款总额
        detail = temp;

        loanData.setTotalMoney(totalMoney);
        loanData.setTotalInterests(totalInterests);
        loanData.setDetail(detail);

        return loanData;
    }

    //等额本金
    /**
     每月还本付息金额=(本金/还款月数)+(本金-累计已还本金)×月利率
     每月本金=总本金/还款月数
     每月利息=(本金-累计已还本金)×月利率
     还款总利息=(还款月数+1)贷款额月利率/2
     还款总额=(还款月数+1)贷款额月利率/2+贷款额
     注意：在等额本金法中，人们每月归还的本金额始终不变，利息随剩余本金的减少而减少，因而其每月还款额逐渐减少。
     */
    public static LoanData EqualPrincipalMethod(LoanData loanData) {
        double principal = loanData.getPrincipal();
        double rate = loanData.getRate();
        int term = loanData.getTerm();

        double totalMoney;
        double totalInterests;
        double detail[][];

        double mIns = rate / 100 / 12; //月利率
        double remains = principal;
        double sum = 0; // 总计还款金额
        double temp[][] = new double[term][3];
        for (int i = 0; i < term; i++)
        {
            temp[i][0] = principal / term;
            temp[i][0] = Math.floor(temp[i][0] * 100 + 0.5) / 100;
            temp[i][1] = remains * mIns;
            temp[i][1] = Math.floor(temp[i][1] * 100 + 0.5) / 100;
            remains -= temp[i][0];
            temp[i][2] = temp[i][0] + temp[i][1];
            temp[i][2] = Math.floor(temp[i][2] * 100 + 0.5) / 100;
            sum += temp[i][2];
        }
        //temp[][0]为每月还款本金 temp[][1]为每月还款利息 temp[][2]为每月还款总额
        detail = temp;
        totalMoney = sum;
        totalMoney = Math.floor(totalMoney * 100 + 0.5) / 100;
        totalInterests = totalMoney - principal;
        totalInterests = Math.floor(totalInterests * 100 + 0.5) / 100;

        loanData.setTotalMoney(totalMoney);
        loanData.setTotalInterests(totalInterests);
        loanData.setDetail(detail);

        return loanData;
    }

}
