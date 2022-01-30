package com.itfxp.mapper;

import com.itfxp.bean.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface UserMapper {
    List<UserInfo> findAllUsers();
}
