package com.yh.manage.course.dao;

import com.lxw.framework.domain.course.CoursePic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursePicRepository extends JpaRepository<CoursePic,String> {
    public Integer deleteByCourseid(String courseId);
}
