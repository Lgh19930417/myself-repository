package com.yh.manage.course.service;

import com.lxw.framework.domain.course.CoursePic;
import com.lxw.framework.model.response.CommonCode;
import com.lxw.framework.model.response.ResponseResult;
import com.yh.manage.course.dao.CoursePicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CoursePicService {

    @Autowired
    private CoursePicRepository coursePicRepository;

    public ResponseResult addCoursePic(String courseId, String pic) {
        CoursePic coursePic = new CoursePic();
        coursePic.setCourseid(courseId);
        coursePic.setPic(pic);
        coursePicRepository.save(coursePic);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    public CoursePic findCoursePicByCourseId(String courseId) {
        Optional<CoursePic> optionalCoursePic = coursePicRepository.findById(courseId);
        if(optionalCoursePic.isPresent()){
            return optionalCoursePic.get();
        }
        return null;
    }
    @Transactional
    public ResponseResult delCoursePicById(String courseId) {
        Integer integer = coursePicRepository.deleteByCourseid(courseId);
        if (integer>0){
           return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }
}
