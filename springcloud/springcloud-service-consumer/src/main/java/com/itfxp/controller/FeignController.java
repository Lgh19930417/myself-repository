package com.itfxp.controller;

import com.itfxp.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feignUser")
public class FeignController {
    @Autowired
    UserClient userClient;
    @GetMapping("/user/queryUserById/{id}")
    public String  queryUserById(@PathVariable("id") Integer id){
        User user = userClient.queryUserById(id);
        return user.toString();
    }
}
