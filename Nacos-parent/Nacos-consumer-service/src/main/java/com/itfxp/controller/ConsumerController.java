package com.itfxp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {
    @Autowired
    LoadBalancerClient loadBalancerClient;
    private String serviceId="nacos-privider-service";
    @Value(value = "${provider.address}")
    String providerUrl;
    @GetMapping(value = "/hello")
    public String invokeConsumer(){
        RestTemplate restTemplate = new RestTemplate();
        String object = restTemplate.getForObject(providerUrl+"/service", String.class);
        return object;
    }

    @GetMapping(value = "/nacos/helloConsumer")
    public  String helloConsumer(){
        ServiceInstance serviceInstance = loadBalancerClient.choose(serviceId);
        URI uri = serviceInstance.getUri();
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri+"/service", String.class);
        System.out.println(uri);
        return result;
    }
}
