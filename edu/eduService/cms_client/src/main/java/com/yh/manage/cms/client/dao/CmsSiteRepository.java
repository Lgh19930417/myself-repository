package com.yh.manage.cms.client.dao;

import com.lxw.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CmsSiteRepository extends MongoRepository<CmsSite,String > {
}
