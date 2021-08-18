package com.mqz.elk.common.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *  版权所有 © Copyright 2012<br>
 *
 * @Author： 蒙大拿
 * @Date：
 * @Description
 * @About： https://github.com/DemoMeng
 */
@Data
@Accessors(chain = true)
public class OffHireBillVO implements Serializable {
    private static final long serialVersionUID = 3489755628076909704L;

    private String startTime;
    private String endTime;
    private BigDecimal houseRent;
    private BigDecimal managementFee;
    private BigDecimal otherAmount;
    //1 = 已到期未缴纳账期  2=本期账期已缴纳
    private String type;

}
