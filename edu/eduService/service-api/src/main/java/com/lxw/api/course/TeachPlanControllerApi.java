package com.lxw.api.course;

import com.lxw.framework.domain.course.Teachplan;
import com.lxw.framework.domain.course.ext.TeachplanNode;
import com.lxw.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api("教学计划管理接口")
public interface TeachPlanControllerApi {
    @ApiOperation("根据课程id查询教学计划")
    @ApiImplicitParam(name = "courseId",value = "课程id" ,type = "String",paramType = "query")
    public TeachplanNode findTeachPlanListByCourseId(String  courseId);

    @ApiOperation("添加教学计划")
    @ApiImplicitParam(name = "teachplan",value = "课程计划" ,type = "Teachplan")
    public ResponseResult addTeachPlanList(Teachplan teachplan);
}
