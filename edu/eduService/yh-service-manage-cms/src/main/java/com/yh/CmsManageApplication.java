package com.yh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan(basePackages = {"com.lxw.api","com.lxw.framework.exception"})
@EntityScan("com.lxw.framework.domain.cms")
@ComponentScan(basePackages = {"com.yh"})
@EnableDiscoveryClient
public class CmsManageApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsManageApplication.class,args);
    }
    @Bean
    public RestTemplate getRestTemplate(){
       return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }
}
