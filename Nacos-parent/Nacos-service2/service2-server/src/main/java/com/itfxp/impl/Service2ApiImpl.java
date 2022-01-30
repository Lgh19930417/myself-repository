package com.itfxp.impl;

import com.itfxp.Service2Api;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class Service2ApiImpl implements Service2Api {

    @Override
    public String service2Api() {
        return "service2Api执行了";
    }
}
