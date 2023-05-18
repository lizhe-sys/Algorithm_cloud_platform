package com.bh.sfapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class ShangFaAPIApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShangFaAPIApplication.class, args);
    }
}