package com.mqz.elk.web.conf;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 版权所有  copyright© 蒙大拿
 *
 * @author mqz
 * @date
 * @about https://www.github.com/DemoMeng
 * @description
 *
 *  knife4j-spring-boot-starter 2.0.6 SpringBoot的版本需要大于2.2.x
 *
 *
 * swagger配置注解和版本关系：
 *
 *  2.2.0 ～ 2.0.5 : @EnableSwagger2 @EnableKnife4j
 *  2.0.6 ~ 2.0.8 : @EnableSwagger2WebMvc
 */
@Configuration
@EnableSwagger2 // knife4j-spring-boot-starter 2.0.4需要该注解
@EnableKnife4j
public class SwaggerConfig {

    @Value("${swagger.enable:false}")
    private boolean enable;

    @Bean(name = "系统管理")
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enable)
                .apiInfo(apiInfo())
                .groupName("系统管理")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mqz.elk.web.controller.system"))
                .paths(PathSelectors.any())
                //.paths(PathSelectors.ant("/api/**"))
                .build();
    }

    @Bean(name = "用户管理")
    public Docket createRestApi1() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enable)
                .apiInfo(apiInfo())
                .groupName("用户管理")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mqz.elk.web.controller.user"))
                .paths(PathSelectors.any())
                //.paths(PathSelectors.ant("/api/**"))
                .build();
    }

    @Bean(name = "swagger-bootstrap-ui")
    public Docket createRestApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enable)
                .apiInfo(apiInfo())
                .groupName("swagger-bootstrap-ui")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mqz.elk.web.controller.swagger"))
                .paths(PathSelectors.any())
                //.paths(PathSelectors.ant("/api/**"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("better-elk在线接口文档")
                .description("蒙大拿")
                .license("copyRight@mqz")
                .description("接口描述文档")
                .termsOfServiceUrl("http://localhost:5000/doc.html")
                .contact("13128507506")
                .version("1.0")
                .build();
    }

}
