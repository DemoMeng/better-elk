package com.mqz.elk.web.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *  版权所有 © Copyright 2012<br>
 *
 * @Author： 蒙大拿
 * @Date：
 * @Description
 * @About： https://github.com/DemoMeng
 */
@RestController
@RequestMapping(value = "/logger")
@Api(value = "测试添加Logger数据",tags = "测试添加Logger数据")
@Slf4j
public class UserController {


    private static List<String> constantList = new ArrayList<>();
    static{
        List<String> list = new ArrayList<>();
        list.add("京飙忠");
        list.add("菅霖思");
        list.add("浮成纶");
        list.add("帖俊宋");
        list.add("龙天飙");
        list.add("蹉哲濯");
        list.add("班灏马");
        list.add("拜生儒");
        list.add("鲁建彰");
        list.add("矫才蒙");
        list.add("范清涛");
        list.add("撒儒东");
        list.add("充泰宪");
        list.add("北朗舷");
        list.add("安信磊");
        list.add("宁腾封");
        list.add("蓬刚安");
        list.add("敏舱宪");
        list.add("池兴劼");
        list.add("多与勰");
        list.add("李豪奎");
        list.add("冼才弓");
        list.add("守望勘");
        constantList = Collections.unmodifiableList(list);
    }

    @GetMapping(value = "/add")
    @ApiOperation(value = "添加",nickname = "add",response = Object.class)
    public Object add(){
        List<String> result = new ArrayList<>();
        for(String name:constantList){
            Random random = new Random();
            String str = name+random.nextInt(99999);
            log.info("【better-elk信息】姓名：{}",str);
            result.add(str);
        }
        return result;
    }

    @PostMapping(value = "/uploadStandard")
    @ApiOperation(value = "上传标准合同模板", notes = "上传标准合同模板")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file",value = "form表单文件,支持doc;docx;pdf格式",required = true),
            @ApiImplicitParam(name = "contractTemplateGroupId", value = "合同模板分组编号",required = true)
    })
    public Object uploadStandard(HttpServletRequest request, @RequestParam MultipartFile[] files,
                                 @RequestParam Long contractTemplateGroupId) {
        return null;
    }



}
