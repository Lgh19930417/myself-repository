package com.yh.service;

import com.alibaba.fastjson.JSON;
import com.lxw.framework.domain.cms.CmsPage;
import com.lxw.framework.domain.cms.CmsSite;
import com.lxw.framework.domain.cms.CmsTemplate;
import com.lxw.framework.domain.cms.request.QueryPageRequest;
import com.lxw.framework.domain.cms.response.CmsCode;
import com.lxw.framework.domain.cms.response.CmsPageResult;
import com.lxw.framework.domain.cms.response.CoursePreviewResult;
import com.lxw.framework.exception.ExceptionCast;
import com.lxw.framework.model.response.CommonCode;
import com.lxw.framework.model.response.QueryResponseResult;
import com.lxw.framework.model.response.QueryResult;
import com.lxw.framework.model.response.ResponseResult;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.yh.config.RabbitmqConfig;
import com.yh.dao.CmsConfigRespository;
import com.yh.dao.CmsPageRepository;
import com.yh.dao.CmsSiteRepository;
import com.yh.dao.CmsTemplateRepository;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class CmsPageService {

    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Autowired
    private CmsConfigRespository cmsConfigRespository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CmsTemplateRepository cmsTemplateRepository;

    @Autowired
    private GridFSBucket gridFSBucket;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CmsSiteRepository cmsSiteRepository;

    public QueryResponseResult findList(QueryPageRequest queryPageRequest, Integer page, Integer size) {
        //??????mongodb????????????????????????0??????,???????????????????????????????????????????????????
        if(page<=0){
            page=1;
        }
        page=page-1;
        //spring?????????????????????????????????
        Pageable pageable = PageRequest.of(page, size);
        //List<CmsPage> cmsPageList = cmsPageRepository.findAll();
        //findAll()??????????????????,??????pageAble??????????????????
        //Page<CmsPage> cmsPages = cmsPageRepository.findAll(pageable);
        /*
        * ????????????????????????????????????,????????????spring.data????????????
        * */

        CmsPage cmsPage = new CmsPage();
        if(StringUtils.isNoneEmpty(queryPageRequest.getSiteId())){
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        if(StringUtils.isNoneEmpty(queryPageRequest.getTemplateId())){
            cmsPage.setTemplateId(queryPageRequest.getTemplateId());
        }
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        //BeanUtils.copyProperties(queryPageRequest,cmsPage);


        if(StringUtils.isNoneEmpty(queryPageRequest.getPageAliase())){
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
            exampleMatcher = exampleMatcher.withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        Example<CmsPage> example = Example.of(cmsPage,exampleMatcher);
        Page<CmsPage> cmsPages = cmsPageRepository.findAll(example, pageable);
        List<CmsPage> cmsPageList = cmsPages.getContent();
        //??????QueryResult???????????????????????????
        QueryResult<CmsPage> cmsPageQueryResult = new QueryResult<>();
        cmsPageQueryResult.setList(cmsPageList);
        cmsPageQueryResult.setTotal(cmsPages.getTotalElements());
        //????????????????????????QueryResponseResult?????????
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS,cmsPageQueryResult);
        return queryResponseResult;
    }
    /**
     * ??????cmsPage??????
     * @param cmsPage
     * @return
     */
    public CmsPageResult addCmsPage(CmsPage cmsPage) {
        //??????????????????
        CmsPage cmsPageDB = cmsPageRepository.findCmsPageBySiteIdAndPageNameAndPageWebPath(
                cmsPage.getSiteId(),cmsPage.getPageName(),cmsPage.getPageWebPath());

        if(cmsPageDB==null){
            cmsPage.setPageId(null);
            //cmsPage.setPageCreateTime(new Date());
            cmsPageRepository.insert(cmsPage);
            return new CmsPageResult(CommonCode.SUCCESS,cmsPage);
        }

        return new CmsPageResult(CommonCode.FAIL,null);
    }


    /**
     * ??????cmsPage????????????
     * @param cmsPage
     * @return
     */
    public CmsPageResult editCmsPage(String id,CmsPage cmsPage){
        CmsPage page = this.findById(id);

        //??????????????????
        if(page!=null){
            page.setPageWebPath(cmsPage.getPageWebPath());
            page.setTemplateId(cmsPage.getTemplateId());
            page.setPageAliase(cmsPage.getPageAliase());
            CmsPage newCmsPage = cmsPageRepository.save(page);
            return new CmsPageResult(CommonCode.SUCCESS,newCmsPage);
        }

        return new CmsPageResult(CommonCode.FAIL,null);

    }

    /**
     * ??????cmsPageId??????????????????
     * @param id
     * @return
     */
    public CmsPage findById(String  id){
        if(StringUtils.isEmpty(id)){
            ExceptionCast.cast(CommonCode.ILLARGS);
        }
        Optional<CmsPage> one = cmsPageRepository.findById(id);
        //CmsPage cmsPage = new CmsPage();
        if(!one.isPresent()){
            ExceptionCast.cast(CommonCode.ILLARGS);

        }
        return one.get();

    }

    /**
     * ??????CMsPage
     * @param id
     * @return
     */
    public ResponseResult delCmsPage(String id){
        CmsPage page = this.findById(id);
        if(page!=null){
            cmsPageRepository.delete(page);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    /**
     * ??????CmsPageId????????????????????????
     * @param cmsPageId
     * @return
     */
    public Map getModelByPageId(String cmsPageId){
        CmsPage cmsPage = this.findById(cmsPageId);
        String dataUrl = cmsPage.getDataUrl();
        ResponseEntity<Map> entity = restTemplate.getForEntity(dataUrl, Map.class);
        Map body = entity.getBody();
        return body;
    }

    /**
     * ??????????????????
     * @param cmsPageId
     * @return
     * @throws IOException
     */
    public String  getHtmlTemplateByCmsPageId(String  cmsPageId) {
        CmsPage cmsPage = this.findById(cmsPageId);
        String templateId = cmsPage.getTemplateId();
        Optional<CmsTemplate> optional = cmsTemplateRepository.findById(templateId);
        if(!optional.isPresent()){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        CmsTemplate cmsTemplate = optional.get();
        String templateFileId = cmsTemplate.getTemplateFileId();
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getFilename());
        GridFsResource gridFsResource = new GridFsResource(gridFSFile,gridFSDownloadStream);
        try {
            InputStream  is = gridFsResource.getInputStream();
            String content = IOUtils.toString(is, "utf-8");
            return content;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
            return null;
    }

    /**
     * ??????CmsPageId????????????html??????
     * @param cmsPageId
     */
    public String  getStaticHtml(String cmsPageId){
        Map modelData = this.getModelByPageId(cmsPageId);
        String htmlTemplate= this.getHtmlTemplateByCmsPageId(cmsPageId);
        Configuration configuration = new Configuration(Configuration.getVersion());
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template",htmlTemplate);
        configuration.setTemplateLoader(stringTemplateLoader);
        try {
            Template template = configuration.getTemplate("template");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, modelData);
            return html;
            /*InputStream inputStream = IOUtils.toInputStream(html, "utf-8");
            FileOutputStream fos = new FileOutputStream(new File("d:/a.html"));
            IOUtils.copy(inputStream, fos);*/
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * ????????????
     * @param pageId
     * @return
     */
    public ResponseResult postPage(String pageId){
        String staticHtml = this.getStaticHtml(pageId);
        //??????cmsPage????????????????????????
        Optional<CmsPage> optionalCmsPage = cmsPageRepository.findById(pageId);
        if(!optionalCmsPage.isPresent()){
            ExceptionCast.cast(CmsCode.CMS_PAGE_EXISTID);
        }
        //??????CmsPage??????htmlFileId
        CmsPage cmsPage = optionalCmsPage.get();
        String htmlFileId = cmsPage.getHtmlFileId();
        //??????GridFS??????file??????
        if(StringUtils.isNoneEmpty(htmlFileId)){
            gridFsTemplate.delete(Query.query(Criteria.where("_id").is(htmlFileId)));
        }

        //????????????staticHtml?????????GridFS???
        ObjectId newId = null;
        try {
            newId = gridFsTemplate.store(IOUtils.toInputStream(staticHtml, "utf-8"), cmsPage.getPageName());
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        //?????????String
        String newStringId = newId.toString();
        cmsPage.setHtmlFileId(newStringId);
        //???newStringId?????????cmsPage???
        CmsPage newCmsPage = cmsPageRepository.save(cmsPage);
        //????????????
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageId",newCmsPage.getPageId());
        String  msg = JSON.toJSONString(map);
        rabbitTemplate.convertAndSend(RabbitmqConfig.EX_ROUTING_CMS_POSTPAGE,cmsPage.getSiteId(),msg);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    public CmsPageResult saveCmsPage(CmsPage cmsPage) {
        if(cmsPage==null){
            ExceptionCast.cast(CommonCode.ILLARGS);
        }
        CmsPage cmsPageDB = cmsPageRepository.findCmsPageBySiteIdAndPageNameAndPageWebPath(cmsPage.getSiteId(), cmsPage.getPageName(), cmsPage.getPageWebPath());
        if(cmsPageDB!=null){
            cmsPage.setPageId(cmsPageDB.getPageId());
            cmsPageRepository.save(cmsPage);
        }else{
            cmsPageRepository.insert(cmsPage);
        }
        return new CmsPageResult(CommonCode.SUCCESS,cmsPage);
    }

    /**
     * ????????????????????????,??????GridFS???,?????????????????????Course??????
     * @param cmsPage
     * @return
     */
    public CoursePreviewResult quickPublish(CmsPage cmsPage) {
        //??????cmsPage????????????
        CmsPageResult cmsPageResult = this.saveCmsPage(cmsPage);
        //???????????????,????????????GridFS???
        if(!cmsPageResult.SUCCESS){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_SAVEHTMLERROR);
        }
        ResponseResult responseResult = this.postPage(cmsPageResult.getCmsPage().getPageId());
        //
        if(!responseResult.SUCCESS){
            ExceptionCast.cast(CommonCode.FAIL);
        }
        //????????????????????????
        Optional<CmsSite> cmsSiteOptional = cmsSiteRepository.findById(cmsPage.getSiteId());
        if(!cmsSiteOptional.isPresent()){
            ExceptionCast.cast(CommonCode.FAIL);
        }
        String siteWebPath = cmsSiteOptional.get().getSiteWebPath();
        String siteDomain = cmsSiteOptional.get().getSiteDomain();
        String pageWebPath = cmsPage.getPageWebPath();

        //??????url
        String url=siteDomain+siteWebPath+pageWebPath;

        return new CoursePreviewResult(CommonCode.SUCCESS,url);
    }
}
