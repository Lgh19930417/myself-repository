package com.itfxp.service;

import com.itfxp.bean.UserInfo;

import java.util.List;

public interface UserService {
    List<UserInfo> findAllUsers();
}
