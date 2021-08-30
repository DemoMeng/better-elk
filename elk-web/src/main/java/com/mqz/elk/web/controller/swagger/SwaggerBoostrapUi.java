package com.mqz.elk.web.controller.swagger;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.mqz.elk.common.model.dto.swagger.OffHireSaveDTO;
import com.mqz.elk.common.model.vo.OffHireDetailVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  版权所有 © Copyright 2012<br>
 *
 * @Author： 蒙大拿
 * @Date：2021/8/18 10:56 上午
 * @Description
 * @About： https://github.com/DemoMeng
 */
@RestController
@RequestMapping(value = "/swagger/boostrap-ui")
@ApiSupport(author = "https://gitee.com/DemoMeng")
@Api(tags = "swagger-bootstap-ui")
@Slf4j
public class SwaggerBoostrapUi {


    @PostMapping(value = "/save")
    @ApiOperation(value = "保存")
    public Object save(@RequestBody OffHireSaveDTO dto){
        log.info("【信息】：{}",dto.toString());
        return null;
    }

    @ApiOperationSupport(author = "https://github.com/DemoMeng")
    @PostMapping(value = "/detail")
    @ApiOperation(value = "回显",response = OffHireDetailVO.class)
    public Object detail(){
        return null;
    }

}
