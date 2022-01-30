package com.itfxp.controller;

import com.itfxp.bean.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "SpringCloudServiceProvider",fallback = FeignHystrixClient.class)
public interface UserClient {
    @GetMapping("/user/queryUserById/{id}")
    public User queryUserById(@PathVariable("id") Integer id);
}
