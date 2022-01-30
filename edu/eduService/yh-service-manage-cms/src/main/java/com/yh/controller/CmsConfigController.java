package com.yh.controller;

import com.lxw.api.cmsManager.CmsConfigControllerApi;
import com.lxw.framework.domain.cms.CmsConfig;
import com.yh.service.CmsConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cms/config")
public class CmsConfigController implements CmsConfigControllerApi {
    @Autowired
    private CmsConfigService cmsConfigService;
    @Override
    @GetMapping("/getmodel/{id}")
    public CmsConfig findCmsConfigById(@PathVariable("id") String cmsPageId) {
        return cmsConfigService.findCmsConfigById(cmsPageId);
    }
}
