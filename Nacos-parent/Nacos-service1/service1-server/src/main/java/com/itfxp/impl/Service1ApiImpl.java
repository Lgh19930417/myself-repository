package com.itfxp.impl;

import com.itfxp.Service1Api;
import com.itfxp.Service2Api;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

@Service
public class Service1ApiImpl implements Service1Api {
    @Reference
    Service2Api service2Api;
    @Override
    public String DubboService1Api() {
        String s = service2Api.service2Api();
        return s;
    }
}
