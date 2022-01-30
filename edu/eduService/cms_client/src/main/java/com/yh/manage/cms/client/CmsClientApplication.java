package com.yh.manage.cms.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.lxw.api","com.lxw.framework.exception","com.yh.manage.cms.client"})
@EntityScan("com.lxw.framework.domain.cms")
@ComponentScan(basePackages = {"com.yh"})
public class CmsClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsClientApplication.class,args);
    }
}
