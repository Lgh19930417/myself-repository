package com.yh.dao;

import com.lxw.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CmsPageRepository extends MongoRepository<CmsPage,String> {
    /**
     * 根据CmsPage的pageName,siteId,pageWebPath查询页面是否存在
     */
    public CmsPage findCmsPageBySiteIdAndPageNameAndPageWebPath(String siteId,String pageName,String pageWebPath);


}
