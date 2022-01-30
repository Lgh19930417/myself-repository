package com.homework.service.impl;

import com.homework.bean.Me;
import com.homework.mapper.MapperMe;
import com.homework.service.ServiceMe;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ServiceMeImpl implements ServiceMe {
    @Autowired
    MapperMe mapperMe;
    @Override
    public Me queryMeById(Integer id) {
        return mapperMe.selectById(id);
    }
}
