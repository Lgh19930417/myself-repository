package com.itfxp.controller;

import com.itfxp.bean.User;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/*@RestController
@RequestMapping("/user")
@DefaultProperties(defaultFallback = "fallbackHandler")*/
public class UserController {
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/queryUsers")
    public List<User> queryUsers(){
        User[] userList = restTemplate.getForObject("http://localhost:10086/user/queryUsers", User[].class);
        return Arrays.asList(userList);
    }
    @Autowired
    DiscoveryClient discoveryClient;
    @GetMapping("/consume/users")
    public List<User> queryUsersEnreka(){
        List<ServiceInstance> instances = discoveryClient.getInstances("SpringCloudServiceProvider");
        ServiceInstance serviceInstance = instances.get(0);
        String host = serviceInstance.getHost();
        int port = serviceInstance.getPort();
        User[] users = restTemplate.getForObject("http://" + host + ":" + port + "/user/queryUsers", User[].class);
        return Arrays.asList(users);
    }
    /*@GetMapping("/ribbon/queryUsers")
    public List<User> queryUsersRibbon(){
        String url="http://SpringCloudServiceProvider/user/queryUsers";
        User[] users = restTemplate.getForObject(url, User[].class);
        return Arrays.asList(users);
    }*/
    @GetMapping("/ribbon/queryUsers")
    /*@HystrixCommand(fallbackMethod = "fallbackHandler")*/
    @HystrixCommand
    public String queryUsersRibbon(){
        String url="http://SpringCloudServiceProvider/user/queryUsers";
        User[] users = restTemplate.getForObject(url, User[].class);
        return Arrays.asList(users).toString();
    }


    @GetMapping("/queryUserById/{id}")
    @HystrixCommand
    public String queryUserById(@PathVariable("id") Integer id){
        if(id==111){
            throw new RuntimeException("滴滴");
        }
        String uri="http://SpringCloudServiceProvider/user/queryUserById/"+id;
       User user = restTemplate.getForObject(uri, User.class);
        return user.toString();
    }

    public String fallbackHandler(){
        return "服务不可用,请稍后再试";
    }

}
