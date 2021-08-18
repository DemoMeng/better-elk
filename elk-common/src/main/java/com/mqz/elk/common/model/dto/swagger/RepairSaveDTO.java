package com.mqz.elk.common.model.dto.swagger;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
  *  版权所有 © Copyright 2012<br>
  *
  * @Author： 蒙大拿
  * @Date：2021/8/17 11:38 上午
  * @Description
  * @About： https://github.com/DemoMeng
  */
@Data
@Accessors(chain = true)
public class RepairSaveDTO implements Serializable{
    @ApiModelProperty(value = "房间id")
    private Integer assetsId;
    @ApiModelProperty(value = "维修进度 (1:已完成 2:未完成 3:无维修)")
    private String repairStatus;
    @ApiModelProperty(value = "维修")
    private List<Detail> detailList;

    public class Detail implements Serializable{
        @ApiModelProperty(value = "楼宇房间内物品资产信息表id")
        private Integer billRoomAssetsId;
        @ApiModelProperty(value = "维修数量不得为空")
        private Integer count;
        @ApiModelProperty(value = "维修金额不得为空")
        private BigDecimal amount;
    }

}