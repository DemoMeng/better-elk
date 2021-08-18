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
public class MeterSaveDTO implements Serializable {
    @ApiModelProperty(value = "房间id")
    private Integer assetsId;
    @ApiModelProperty(value = "房屋状态 1：毛坯， 2：装修")
    private String roomStatus;
    @ApiModelProperty(value = "水电费是否欠费 1:欠费 2:不欠费")
    private String meterStatus;
    @ApiModelProperty(value = "仪表")
    private List<Detail> detailList;

    @Data
    public class Detail implements Serializable{
        @ApiModelProperty(value = "类型 1:水表 2:电表 3:空调表")
        private String type;
        @ApiModelProperty(value = "仪表编号")
        private String meterNo;
        @ApiModelProperty(value = "仪表度数")
        private BigDecimal meterDegree;
    }
}