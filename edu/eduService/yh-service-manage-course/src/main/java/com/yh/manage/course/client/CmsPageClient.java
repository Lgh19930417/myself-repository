package com.yh.manage.course.client;

import com.lxw.framework.domain.cms.CmsPage;
import com.lxw.framework.domain.cms.response.CmsPageResult;
import com.lxw.framework.domain.cms.response.CoursePreviewResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("yh-service-manage-cms")
public interface CmsPageClient {

    @GetMapping("cms/page/get/{id}")
    public CmsPage findById(@PathVariable String id);

    @PostMapping("/cms/page/save/")
    public CmsPageResult saveCmsPage(@RequestBody CmsPage cmsPage);

    @PostMapping("/cms/page/postPageQuick")
    public CoursePreviewResult quickPublish(@RequestBody CmsPage cmsPage);
}
