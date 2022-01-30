package com.yh.controller;

import com.lxw.api.cmsManager.CmsPageControllerApi;
import com.lxw.framework.domain.cms.CmsPage;
import com.lxw.framework.domain.cms.request.QueryPageRequest;
import com.lxw.framework.domain.cms.response.CmsPageResult;
import com.lxw.framework.domain.cms.response.CoursePreviewResult;
import com.lxw.framework.model.response.QueryResponseResult;
import com.lxw.framework.model.response.ResponseResult;
import com.yh.service.CmsPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cms/page")
public class CmsPageController implements CmsPageControllerApi {
    @Autowired
    private CmsPageService cmsManagerService;

    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findList(QueryPageRequest queryPageRequest,
                                        @PathVariable("page") Integer page,
                                        @PathVariable("size") Integer size) {
        QueryResponseResult queryResponseResultList=cmsManagerService.findList(queryPageRequest,page,size);
        return queryResponseResultList;
    }
    /**
     * 新增cmsPage页面
     * @param cmsPage
     * @return
     */
    @PostMapping("/add")
    @Override
    public CmsPageResult addCmsPage(@RequestBody CmsPage cmsPage) {
       CmsPageResult cmsPageResult= cmsManagerService.addCmsPage(cmsPage);
        return cmsPageResult;
    }
    /**
     * 更新cmsPage页面信息
     * @param cmsPage
     * @return
     */
    @Override
    @PutMapping("/edit/{id}")
    public CmsPageResult editCmsPage(@PathVariable String id,@RequestBody CmsPage cmsPage) {
        CmsPageResult cmsPageResult = cmsManagerService.editCmsPage(id,cmsPage);
        return cmsPageResult;
    }
    /**
     * 根据cmsPageId查询是否存在
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public CmsPage findById(@PathVariable String id){
        CmsPage cmspage = cmsManagerService.findById(id);
        return cmspage;
    }

    /**
     * 删除cmsPage页面
     * @param id
     * @return
     */
    @DeleteMapping ("/del/{id}")
    public ResponseResult delCmsPage(@PathVariable String  id){
        ResponseResult responseResult = cmsManagerService.delCmsPage(id);
        return responseResult;
    }

    @Override
    @PostMapping("/postPage/{pageId}")
    public ResponseResult postPageByPageId(@PathVariable("pageId") String pageId) {

        return cmsManagerService.postPage(pageId);
    }

    /**
     * 添加课程详情页面
     * @param cmsPage
     * @return
     */
    @Override
    @PostMapping("/save/")
    public CmsPageResult saveCmsPage(@RequestBody CmsPage cmsPage) {

        return cmsManagerService.saveCmsPage(cmsPage);
    }

    /**
     * 生成课程详情预览页面
     * @param cmsPage
     * @return
     */
    @Override
    @PostMapping("/postPageQuick")
    public CoursePreviewResult quickPublish(@RequestBody CmsPage cmsPage) {
        return cmsManagerService.quickPublish(cmsPage);
    }

}
