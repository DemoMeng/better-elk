package com.mqz.elk.common.model.dto.swagger;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 *  版权所有 © Copyright 2012<br>
 *
 * @Author： 蒙大拿
 * @Date：2021/5/24 5:42 下午
 * @Description
 * @About： https://github.com/DemoMeng
 */
@Data
@Accessors(chain = true)
public class OffHireSaveDTO implements Serializable {
    private static final long serialVersionUID = 5789385605820537868L;
    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "当前钉钉userId")
    private String currentDingUserId;
    @ApiModelProperty(value = "退租协议（内退企业附情况说明）")
    private List<String> offHireDocumentAttachment;
    @ApiModelProperty(value = "房屋仪表列表")
    private List<MeterSaveDTO> meterSaveList;
    @ApiModelProperty(value = "房屋仪表列表")
    private List<RepairSaveDTO> repairSaveList;
}
