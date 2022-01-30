package com.yh.manage.course.controller;

import com.lxw.api.course.CoursePicControllerApi;
import com.lxw.framework.domain.course.CoursePic;
import com.lxw.framework.model.response.ResponseResult;
import com.yh.manage.course.service.CoursePicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
public class CoursePicController implements CoursePicControllerApi {
    @Autowired
    private CoursePicService coursePicService;


    @PostMapping("/coursepic/add")
    @Override
    public ResponseResult addCoursePic(String courseId, String pic) {
        return coursePicService.addCoursePic(courseId,pic);
    }
    @GetMapping("/coursepic/list/{courseId}")
    @Override
    public CoursePic findCoursePicByCourseId(@PathVariable("courseId") String courseId) {
        return coursePicService.findCoursePicByCourseId(courseId);
    }

    @DeleteMapping("/coursepic/delete")
    @Override
    public ResponseResult delCoursePicById(@RequestParam("courseId") String courseId){
        return coursePicService.delCoursePicById(courseId);
    }
}
