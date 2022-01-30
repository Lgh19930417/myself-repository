package com.lxw.api.cmsManager;

import com.lxw.framework.domain.cms.CmsConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api("获取CmsConfig模型数据,包含增删查改等")
public interface CmsConfigControllerApi {
    /**
     * 获取CmsConfig中静态页面的模型数据
     * @param cmsPageId
     * @return
     */
    @ApiOperation("查询CmsConfig中的页面模型数据")
    @ApiParam(name = "cmsPageId",value = "前端传过来的CmsPageId",type = "String")
    public CmsConfig findCmsConfigById(String cmsPageId);
}
