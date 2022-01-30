package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SpringBootWithRabbitmq {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootWithRabbitmq.class,args);
    }
    @Bean
    public RestTemplate remoteTest(){
        return new RestTemplate();
    }
}
