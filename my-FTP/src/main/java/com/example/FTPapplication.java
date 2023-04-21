package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author duran
 * @description TODO
 * @date 2023-04-15
 */
@SpringBootApplication
public class FTPapplication {
    public static void main(String[] args) {
        SpringApplication.run(FTPapplication.class, args);
    }
    @Bean
//    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }


//    @Bean(name = "restTemplateHttps")
//    @LoadBalanced
//    RestTemplate restTemplateHttps() {
//        return new RestTemplate(new HttpsClientRequestFactory());
//    }

}
