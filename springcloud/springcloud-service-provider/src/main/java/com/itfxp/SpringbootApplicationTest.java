package com.itfxp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.itfxp.mapper")
@EnableEurekaClient
public class SpringbootApplicationTest {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplicationTest.class,args);
    }
}
