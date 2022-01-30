package com.itfxp.service;


import com.itfxp.Service1Api;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NacosService {
    @Reference
    Service1Api service1Api;
    @GetMapping("/nacos/service1")
    public String DubboCallService1(){
        String s = service1Api.DubboService1Api();
        return s;
    }
    @Value("${consumer.common.name}")
    public String commonName;
    @GetMapping("/config")
    public String getConfig(){
        return commonName;
    }
    @Autowired
    public ConfigurableApplicationContext context;
    @GetMapping("/getConfigAuto")
    public String getConfigAuto(){
        String name = context.getEnvironment().getProperty("consumer.common.name");
        String addr = context.getEnvironment().getProperty("consumer.common.addr");
        return name+addr;
    }
}
