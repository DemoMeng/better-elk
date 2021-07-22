package com.mqz.elk.common.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 *  版权所有 © Copyright 2012<br>
 *
 * @Author： 蒙大拿
 * @Date：
 * @Description
 * @About： https://github.com/DemoMeng
 */
@ApiModel(value = "关键字自动补全请求实体",description = "关键字自动补全请求实体")
@Data
@Accessors(chain = true)
public class PoemKeywordCompletionDTO implements Serializable {

    private String searchField;
    private String searchValue;
    private Integer searchMaxCount;
    private String index;
    private String type;

}
