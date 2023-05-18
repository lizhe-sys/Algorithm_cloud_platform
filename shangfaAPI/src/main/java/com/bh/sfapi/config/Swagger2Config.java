//package com.bh.sfapi.config;
//
//import com.google.common.base.Predicates;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
///**
// * Swagger2配置信息
// */
//@Configuration
//@EnableSwagger2
//public class Swagger2Config {
//
//    @Bean
//    public Docket mysqlApiConfig(){
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("mysqlApi")
//                .apiInfo(webApiInfo())
//                .select()
//                //只显示api路径下的页面
//                .paths(Predicates.and(PathSelectors.regex("/sf/mysql/.*")))
//                .build();
//    }
//
//    @Bean
//    public Docket influxdbApiConfig(){
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("influxdbApi")
//                .apiInfo(webApiInfo())
//                .select()
//                //只显示api路径下的页面
//                .paths(Predicates.and(PathSelectors.regex("/sf/influxdb/.*")))
//                .build();
//    }
//
//    @Bean
//    public Docket dockerApiConfig(){
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("dockerApi")
//                .apiInfo(webApiInfo())
//                .select()
//                //只显示api路径下的页面
//                .paths(Predicates.and(PathSelectors.regex("/sf/docker/.*")))
//                .build();
//    }
//    private ApiInfo webApiInfo(){
//        return new ApiInfoBuilder()
//                .title("网站-API文档")
//                .description("本文档描述了网站微服务接口定义")
//                .version("1.0")
//                .contact(new Contact("xxxx", "", ""))
//                .build();
//    }
//
//    private ApiInfo adminApiInfo(){
//        return new ApiInfoBuilder()
//                .title("后台管理系统-API文档")
//                .description("本文档描述了后台管理系统微服务接口定义")
//                .version("1.0")
//                .contact(new Contact("xxxx", "", ""))
//                .build();
//    }
//}
