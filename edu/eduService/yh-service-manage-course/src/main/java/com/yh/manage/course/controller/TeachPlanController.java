package com.yh.manage.course.controller;

import com.lxw.api.course.TeachPlanControllerApi;
import com.lxw.framework.domain.course.Teachplan;
import com.lxw.framework.domain.course.ext.TeachplanNode;
import com.lxw.framework.model.response.ResponseResult;
import com.yh.manage.course.service.TeachPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
public class TeachPlanController implements TeachPlanControllerApi {
    @Autowired
    private TeachPlanService teachPlanService;


    @Override
    @GetMapping("/teachplan/list/{courseId}")
    public TeachplanNode findTeachPlanListByCourseId(@PathVariable("courseId") String courseId) {
        TeachplanNode teachplanNode = teachPlanService.findTeachPlanListByCourseId(courseId);
        return teachplanNode;
    }


    @PostMapping("/teachplan/add")
    @Override
    public ResponseResult addTeachPlanList(@RequestBody Teachplan teachplan) {
        return teachPlanService.addTeachplan(teachplan);
    }
}
