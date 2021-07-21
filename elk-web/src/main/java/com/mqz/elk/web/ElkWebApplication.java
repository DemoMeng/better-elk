package com.mqz.elk.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * 版权所有 © Copyright 2012<br>
 *
 * @Author： 蒙大拿
 * @Date：
 * @Description
 * @About： https://github.com/DemoMeng
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.mqz.elk.web.**"})
@EnableElasticsearchRepositories(basePackages = {"com.mqz.elk.web.**"})
public class ElkWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElkWebApplication.class, args);
    }

}
