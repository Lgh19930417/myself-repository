package com.yh.dao;

import com.lxw.framework.domain.cms.CmsConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CmsConfigRespository extends MongoRepository<CmsConfig,String > {
}
