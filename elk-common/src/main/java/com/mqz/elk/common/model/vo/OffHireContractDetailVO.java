package com.mqz.elk.common.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 *  版权所有 © Copyright 2012<br>
 *
 * @Author： 蒙大拿
 * @Date：2021/5/25 9:23 上午
 * @Description
 * @About： https://github.com/DemoMeng
 */

@Data
@Accessors(chain = true)
public class OffHireContractDetailVO implements Serializable {
    private static final long serialVersionUID = -8321614811136111552L;

    @ApiModelProperty(value = "详情id")
    private Integer detailId;
    @ApiModelProperty(value = "1:办公楼   2：宿舍楼'' 3:厂房 4:商铺")
    private String roomType;
    @ApiModelProperty(value = "维修列表")
    private List<OffHireDetailPropertyVO> propertyList;
}
