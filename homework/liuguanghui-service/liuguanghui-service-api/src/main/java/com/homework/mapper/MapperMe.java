package com.homework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.homework.bean.Me;

public interface MapperMe extends BaseMapper<Me> {
    Me queryMeById(Integer id);
}
