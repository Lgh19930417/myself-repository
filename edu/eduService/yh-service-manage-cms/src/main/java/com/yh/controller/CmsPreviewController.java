package com.yh.controller;

import com.lxw.framework.web.BaseController;
import com.yh.service.CmsPageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Slf4j
public class CmsPreviewController extends BaseController {
    @Autowired
    private CmsPageService cmsPageService;

    @RequestMapping(value = "/cms/preview/{pageId}",method = RequestMethod.GET)
    public void preview(@PathVariable("pageId") String cmsPageId){
        String staticHtml = cmsPageService.getStaticHtml(cmsPageId);
        try {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(staticHtml);
        } catch (Exception e) {
            log.error("预览异常:{}",e.getMessage());
            e.printStackTrace();
        }
    }
}
