package com.lxw.api.course;

import com.lxw.framework.domain.cms.response.CoursePreviewResult;
import com.lxw.framework.domain.course.ext.CourseView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api("课程管理")
public interface CourseControllerApi {

    @ApiOperation("根据课程id获取课程详细信息")
    @ApiImplicitParam(name = "courseId",value = "课程id",paramType = "path",dataType = "String")
    public CourseView courseView(String courseId);

    @ApiOperation("课程预览")
    @ApiImplicitParam(name = "courseId",value = "课程id",paramType = "path",dataType = "String")
    public CoursePreviewResult preview(String courseId);

    @ApiOperation("课程发布")
    @ApiImplicitParam(name = "courseId",value = "课程id",paramType = "path",dataType = "String")
    public CoursePreviewResult coursePublish(String courseId);
}
