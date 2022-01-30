package com.homework.controller;

import com.homework.bean.Me;
import com.homework.service.ServiceMe;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerMe {
    @Reference
    ServiceMe serviceMe;
    @GetMapping("/queryMeById/{id}")
    public Me queryMeById(@PathVariable("id") Integer id){
        Me me = serviceMe.queryMeById(id);
        return me;
    }
}
