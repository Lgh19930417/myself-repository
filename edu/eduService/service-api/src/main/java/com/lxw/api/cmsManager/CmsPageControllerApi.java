package com.lxw.api.cmsManager;

import com.lxw.framework.domain.cms.CmsPage;
import com.lxw.framework.domain.cms.request.QueryPageRequest;
import com.lxw.framework.domain.cms.response.CmsPageResult;
import com.lxw.framework.domain.cms.response.CoursePreviewResult;
import com.lxw.framework.model.response.QueryResponseResult;
import com.lxw.framework.model.response.ResponseResult;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Api(tags = "cms页面功能",description = "包含增删查改")
public interface CmsPageControllerApi {
    /**
     * 根据条件查询cmsPage列表
     * @param queryPageRequest
     * @param page
     * @param size
     * @return
     */
    @ApiOperation("/查询cmsPage列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryPageRequest",value = "查询page请求实体类",required = false,dataType = "QueryPageRequest"),
            @ApiImplicitParam(name = "page",value = "从第几页开始查询",required = true,paramType = "path",dataType = "Integer"),
            @ApiImplicitParam(name = "size",value = "每页显示条数",required = true,paramType = "path",dataType = "Integer")
    })
    public QueryResponseResult findList(QueryPageRequest queryPageRequest, Integer page, Integer size);


    /**
     * 新增cmsPage页面
     * @param cmsPage
     * @return
     */
    @ApiOperation("新增cmsPage页面")
    @ApiParam(name = "cmsPage",value = "cmsPage页面",required = true,type ="CmsPage" )
    public CmsPageResult addCmsPage(@RequestBody CmsPage cmsPage);

    /**
     * 修改cmsPage页面
     * @param cmsPage
     * @return
     */
    public CmsPageResult editCmsPage(@PathVariable String id, @RequestBody CmsPage cmsPage);

    /**
     * 删除cmsPage页面
     * @param id
     * @return
     */
    @ApiOperation("删除cmsPage")
    public ResponseResult delCmsPage(@PathVariable String id);


    @ApiOperation("发布cmsPage页面")
    public ResponseResult postPageByPageId(String pageId);

    @ApiOperation("新增或更新cmsPage页面")
    @ApiParam(name = "cmsPage",value = "cmsPage页面",required = true,type ="CmsPage" )
    public CmsPageResult saveCmsPage(@RequestBody CmsPage cmsPage);


    @ApiOperation("一键发布cmsPage页面")
    @ApiParam(name = "cmsPage",value = "cmsPage页面",required = true,type ="CmsPage" )
    public CoursePreviewResult quickPublish(CmsPage cmsPage);

}
