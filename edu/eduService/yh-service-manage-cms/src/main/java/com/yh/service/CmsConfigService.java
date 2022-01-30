package com.yh.service;

import com.lxw.framework.domain.cms.CmsConfig;
import com.lxw.framework.exception.ExceptionCast;
import com.lxw.framework.model.response.CommonCode;
import com.yh.dao.CmsConfigRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CmsConfigService {

    @Autowired
    private CmsConfigRespository cmsConfigRespository;

    public CmsConfig findCmsConfigById(String cmsPageId){
        //关键参数校验
        if(cmsPageId==null){
            ExceptionCast.cast(CommonCode.ILLARGS);
        }
        Optional<CmsConfig> optional = cmsConfigRespository.findById(cmsPageId);
       // CmsConfig cmsConfig = optional.orElse(null);
        return optional.orElse(null);
    }
}
