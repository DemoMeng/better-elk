package com.mqz.elk.web.controller.system;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  版权所有 © Copyright 2012<br>
 *
 * @Author： 蒙大拿
 * @Date：
 * @Description
 * @About： https://github.com/DemoMeng
 */
@RestController
@RequestMapping(value = "/system/menu")
@Api(value = "系统控制-菜单管理",tags = "系统控制-菜单管理")
public class MenuController {

    @GetMapping(value = "/list")
    @ApiOperation(value = "菜单管理列表",nickname = "list",response = Object.class)
    public Object list(){
        return "OK";
    }

}
