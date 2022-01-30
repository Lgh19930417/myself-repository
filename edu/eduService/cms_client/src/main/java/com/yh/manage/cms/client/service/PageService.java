package com.yh.manage.cms.client.service;

import com.lxw.framework.domain.cms.CmsPage;
import com.lxw.framework.domain.cms.CmsSite;
import com.lxw.framework.domain.cms.response.CmsCode;
import com.lxw.framework.exception.ExceptionCast;
import com.lxw.framework.model.response.CommonCode;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.yh.manage.cms.client.dao.CmsPageRepository;
import com.yh.manage.cms.client.dao.CmsSiteRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Optional;

@Service
@Slf4j
public class PageService {

    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Autowired
    private CmsSiteRepository cmsSiteRepository;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFSBucket gridFSBucket;
    //查询页面并下载页面
    public void downloadHtmlByCmsPageId(String pageId) {

        Optional<CmsPage> optionalCmsPage = cmsPageRepository.findById(pageId);
        if (!optionalCmsPage.isPresent()) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_EXISTID);
        }
        CmsPage cmsPage = optionalCmsPage.get();
        //获取html页面的目标路径
        String path = this.getHtmlDownloadPath(cmsPage);

        //在GridFS上下载页面
        String htmlFileId = cmsPage.getHtmlFileId();
        if (StringUtils.isEmpty(htmlFileId)) {
            ExceptionCast.cast(CommonCode.ILLARGS);
        }
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(htmlFileId)));
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());

        try {
            GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
            InputStream is = gridFsResource.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(new File(path + cmsPage.getPageName()));
            IOUtils.copy(is, fileOutputStream);
            is.close();
            fileOutputStream.close();
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }


    }


    //获取html页面的目标路径
    private String  getHtmlDownloadPath(CmsPage cmsPage){

        try {
            Optional<CmsSite> siteOptional = cmsSiteRepository.findById(cmsPage.getSiteId());
            if(!siteOptional.isPresent()){
                ExceptionCast.cast(CmsCode.CMS_PAGE_EXISTID);
            }
            CmsSite cmsSite = siteOptional.get();
            //CmsSite cmsSite = siteOptional.orElse(null);
            String path = cmsSite.getSitePhysicalPath()+cmsPage.getPagePhysicalPath();
            return path;
            /*FileOutputStream fos = new FileOutputStream(new File(path));
            IOUtils.copy(is,fos);*/
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return null;
    }
}
