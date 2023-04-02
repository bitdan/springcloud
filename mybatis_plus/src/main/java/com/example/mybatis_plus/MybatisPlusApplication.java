package com.example.mybatis_plus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.mybatis_plus.mapper,com.example.mybatis_plus.product.mapper")
//@ComponentScan(basePackages = "com.example.mybatis_plus.aspect")
public class MybatisPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusApplication.class, args);
    }

}
