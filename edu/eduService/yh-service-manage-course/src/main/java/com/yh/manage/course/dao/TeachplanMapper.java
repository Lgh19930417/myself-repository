package com.yh.manage.course.dao;

import com.lxw.framework.domain.course.ext.TeachplanNode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 课程计划mapper
 * Created by Administrator.
 */
@Mapper
public interface TeachplanMapper {
    //课程计划查询
    public TeachplanNode selectList(@Param("courseId") String courseId);
}
