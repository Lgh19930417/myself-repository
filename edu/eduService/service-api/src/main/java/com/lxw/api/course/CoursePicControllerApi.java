package com.lxw.api.course;

import com.lxw.framework.domain.course.CoursePic;
import com.lxw.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api("课程图片管理")
public interface CoursePicControllerApi {
    @ApiOperation("课程图片上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId",value = "课程id",paramType = "query",type = "String",required = true),
            @ApiImplicitParam(name = "pic",value = "课程图片位置",paramType = "query",type = "String",required = true)
    })
    public ResponseResult addCoursePic(String courseId,String pic);

    @ApiOperation("课程图片查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId",value = "课程id",paramType = "path",type = "String",required = true),
    })
    public CoursePic findCoursePicByCourseId(String courseId);

    @ApiOperation("课程图片删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId",value = "课程id",paramType = "path",type = "String",required = true),
    })
    public ResponseResult delCoursePicById(String courseId);
}
