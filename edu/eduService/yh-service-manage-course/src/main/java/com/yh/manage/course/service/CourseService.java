package com.yh.manage.course.service;

import com.alibaba.fastjson.JSON;
import com.lxw.framework.domain.cms.CmsPage;
import com.lxw.framework.domain.cms.response.CmsPageResult;
import com.lxw.framework.domain.cms.response.CoursePreviewResult;
import com.lxw.framework.domain.course.CourseBase;
import com.lxw.framework.domain.course.CourseMarket;
import com.lxw.framework.domain.course.CoursePic;
import com.lxw.framework.domain.course.CoursePub;
import com.lxw.framework.domain.course.ext.CourseView;
import com.lxw.framework.domain.course.ext.TeachplanNode;
import com.lxw.framework.domain.course.response.CourseCode;
import com.lxw.framework.exception.ExceptionCast;
import com.lxw.framework.model.response.CommonCode;
import com.yh.manage.course.client.CmsPageClient;
import com.yh.manage.course.dao.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseBaseRepository courseBaseRepository;
    @Autowired
    private CoursePicRepository coursePicRepository;
    @Autowired
    private CourseMarketRepository courseMarketRepository;
    @Autowired
    private TeachplanMapper teachplanMapper;
    @Autowired
    private CoursePubRespository coursePubRespository;
    @Value("${course‐publish.siteId}")
    private String siteId;
    @Value("${course‐publish.templateId}")
    private String templateId;
    @Value("${course‐publish.previewUrl}")
    private String previewUrl;
    @Value("${course‐publish.pageWebPath}")
    private String pageWebPath;
    @Value("${course‐publish.pagePhysicalPath}")
    private String pagePhysicalPath;
    @Value("${course‐publish.dataUrlPre}")
    private String dataUrlPre;

    @Autowired
    private CmsPageClient cmsPageClient;

    public CourseView courseView(String courseId) {
        CourseBase courseBase = courseBaseRepository.findById(courseId).orElse(null);
        CoursePic coursePic = coursePicRepository.findById(courseId).orElse(null);
        CourseMarket courseMarket = courseMarketRepository.findById(courseId).orElse(null);
        TeachplanNode teachplanNode = teachplanMapper.selectList(courseId);
        CourseView courseView = new CourseView();
        courseView.setCourseBase(courseBase);
        courseView.setCoursePic(coursePic);
        courseView.setCourseMarket(courseMarket);
        courseView.setTeachplanNode(teachplanNode);
        return courseView;
    }

    /**
     * 课程预览
     * @param courseId
     * @return
     */
    public CoursePreviewResult preview(String courseId) {
        //判断courseBase是否存在,
        Optional<CourseBase> courseBaseOptional = courseBaseRepository.findById(courseId);
        if(!courseBaseOptional.isPresent()){
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_VIEWERROR);
        }
        CourseBase courseBase= courseBaseOptional.get();
    //创建CmsPage对象
        CmsPage cmsPage = this.doneCmsPage(courseBase);
        //添加或者更新cmsPage信息
        CmsPageResult cmsPageResult = cmsPageClient.saveCmsPage(cmsPage);
        //组装预览课程详情页面的url
        String url=previewUrl+cmsPageResult.getCmsPage().getPageId();
        CoursePreviewResult coursePreviewResult = new CoursePreviewResult(CommonCode.SUCCESS, url);
        return coursePreviewResult;
    }

    /**
     * 组装CmsPage页面
     * @param courseBase
     * @return
     */
    private CmsPage doneCmsPage(CourseBase courseBase){

        CmsPage cmsPage = new CmsPage();
        cmsPage.setPageCreateTime(new Date());
        cmsPage.setPageAliase(courseBase.getName());
        cmsPage.setSiteId(siteId);
        cmsPage.setTemplateId(templateId);
        cmsPage.setPageWebPath(pageWebPath);
        cmsPage.setPagePhysicalPath(pagePhysicalPath);
        cmsPage.setDataUrl(dataUrlPre+courseBase.getId());
        cmsPage.setPageName(courseBase.getId()+".html");
        return cmsPage;
    }

    /**
     * 课程发布
     * @param courseId
     * @return
     */
    public CoursePreviewResult coursePublish(String courseId) {
        //判断courseBase是否存在,
        Optional<CourseBase> courseBaseOptional = courseBaseRepository.findById(courseId);
        if(!courseBaseOptional.isPresent()){
           ExceptionCast.cast(CourseCode.COURSE_PUBLISH_VIEWERROR);
        }
        CourseBase courseBase= courseBaseOptional.get();
        //组装cmsPage
        CmsPage cmsPage = doneCmsPage(courseBase);
        //远程调用cms服务,静态化
        CoursePreviewResult coursePreviewResult = cmsPageClient.quickPublish(cmsPage);
        //更新课程状态
        if(!coursePreviewResult.SUCCESS){
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_VIEWERROR);
        }
         courseBase.setStatus("202002");
        courseBaseRepository.save(courseBase);
        CoursePub coursePub = doneCoursePub(courseBase);
        coursePubRespository.save(coursePub);
        return coursePreviewResult;
    }
    private CoursePub doneCoursePub(CourseBase courseBase){
        CoursePub coursePub = new CoursePub();
        BeanUtils.copyProperties(courseBase,coursePub);
        TeachplanNode teachplanNode = teachplanMapper.selectList(courseBase.getId());
        String teachplan = JSON.toJSONString(teachplanNode);
        coursePub.setTeachplan(teachplan);
        Optional<CourseMarket> courseMarketOptional = courseMarketRepository.findById(courseBase.getId());
        CourseMarket courseMarket = courseMarketOptional.orElse(null);
        BeanUtils.copyProperties(courseMarket,coursePub);
        Optional<CoursePic> coursePicRepositoryOptional = coursePicRepository.findById(courseBase.getId());
        CoursePic coursePic = coursePicRepositoryOptional.orElse(null);
        BeanUtils.copyProperties(coursePic,coursePub);
        coursePub.setTimestamp(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String pubDate = simpleDateFormat.format(new Date());
        coursePub.setPubTime(pubDate);

        return coursePub;
    }

}
