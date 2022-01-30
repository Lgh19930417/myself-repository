package com.itfxp.controller;

import com.itfxp.bean.User;
import org.springframework.stereotype.Component;

@Component
public class FeignHystrixClient implements UserClient{
    @Override
    public User  queryUserById(Integer id) {
        User user = new User();
        user.setUsername("服务器异常");
        return user;
    }
}
