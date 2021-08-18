package com.mqz.elk.common.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *  版权所有 © Copyright 2012<br>
 *
 * @Author： 蒙大拿
 * @Date：2021/8/16 1:36 下午
 * @Description
 * @About： https://github.com/DemoMeng
 */
@Data
@Accessors(chain = true)
public class OffHireDetailPropertyVO implements Serializable {
    private static final long serialVersionUID = -7187956672282077054L;

    @ApiModelProperty(value="合同退住表主键")
    private Integer offHireId;
    @ApiModelProperty(value="楼宇房间内物品资产信息表id")
    private Integer billRoomAssetsId;
    @ApiModelProperty(value="名称")
    private String propertyName;
    @ApiModelProperty(value="维修数量")
    private Integer count;
    @ApiModelProperty(value="维修金额")
    private BigDecimal amount;
    @ApiModelProperty(value="维修时间")
    private Date repairTime;

}
