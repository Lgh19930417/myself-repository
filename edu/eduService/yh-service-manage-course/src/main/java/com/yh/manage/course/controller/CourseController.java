package com.yh.manage.course.controller;

import com.lxw.api.course.CourseControllerApi;
import com.lxw.framework.domain.cms.CmsPage;
import com.lxw.framework.domain.cms.response.CoursePreviewResult;
import com.lxw.framework.domain.course.ext.CourseView;
import com.yh.manage.course.client.CmsPageClient;
import com.yh.manage.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
public class CourseController implements CourseControllerApi {
    @Autowired
    private CmsPageClient  cmsPageClient;
    @Autowired
    private CourseService courseService;

    @GetMapping("/get/{id}")
    public CmsPage getById(@PathVariable("id") String cmsPageId){
        CmsPage cms = cmsPageClient.findById(cmsPageId);
        return cms;
    }

    /**
     * 获取课程的详细情况模型数据
     * @param courseId
     * @return
     */
    @Override
    @GetMapping("/courseview/{id}")
    public CourseView courseView(@PathVariable("id") String courseId) {
        return courseService.courseView(courseId);
    }

    @Override
    @PostMapping("/preview/{id}")
    public CoursePreviewResult preview(@PathVariable("id") String courseId) {
        return courseService.preview(courseId);
    }

    @Override
    @PostMapping("/publish/{id}")
    public CoursePreviewResult coursePublish(@PathVariable("id") String courseId) {

        return courseService.coursePublish(courseId);
    }
}
