package com.yh.manage.course.service;

import com.lxw.framework.domain.course.CourseBase;
import com.lxw.framework.domain.course.Teachplan;
import com.lxw.framework.domain.course.ext.TeachplanNode;
import com.lxw.framework.exception.ExceptionCast;
import com.lxw.framework.model.response.CommonCode;
import com.lxw.framework.model.response.ResponseResult;
import com.yh.manage.course.dao.CourseBaseRepository;
import com.yh.manage.course.dao.TeachplanMapper;
import com.yh.manage.course.dao.TeachplanRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeachPlanService {

    @Autowired
    private TeachplanMapper teachplanMapper;
    @Autowired
    private TeachplanRepository teachplanRepository;
    @Autowired
    private CourseBaseRepository courseBaseRepository;


    public TeachplanNode findTeachPlanListByCourseId(String courseId){
        TeachplanNode teachplanNode = teachplanMapper.selectList(courseId);
        return teachplanNode;
    }


    public ResponseResult addTeachplan(Teachplan teachplan) {
        //关键参数校验
        if(teachplan==null|| StringUtils.isEmpty(teachplan.getCourseid())||StringUtils.isEmpty(teachplan.getPname())){
            ExceptionCast.cast(CommonCode.ILLARGS);
        }
        //判断是否有parentId,如果不为空,证明为三级节点,设置等级为3,然后保存
        if(!StringUtils.isEmpty(teachplan.getParentid())){
            teachplan.setGrade("3");
            teachplanRepository.save(teachplan);
            return new ResponseResult(CommonCode.SUCCESS);
        }else{
            //如果没有parentId,证明为二级节点,其根节点的parentId一定为0,
            // 先查询此二级节点的根节点,如果有根节点,就保存此二级节点,否则,就创建根节点,并保存根节点和二级节点

            //查询二级节点的根节点
            List<Teachplan> teachplanList = teachplanRepository.findByCourseidAndParentid(teachplan.getCourseid(), "0");
            //如果查询结果为空,说明没有根节点,就创建一个根节点,并保存根节点和此二级节点
            if (teachplanList==null){
                Optional<CourseBase> optionalCourseBase = courseBaseRepository.findById(teachplan.getCourseid());
                if(!optionalCourseBase.isPresent()){
                    ExceptionCast.cast(CommonCode.ILLARGS);
                }
                CourseBase courseBase = optionalCourseBase.get();
                Teachplan teachplanRoot = new Teachplan();
                teachplanRoot.setGrade("1");
                teachplanRoot.setCourseid(courseBase.getId());
                teachplanRoot.setParentid("0");
                teachplanRoot.setStatus("0");
                teachplanRoot.setPname(courseBase.getName());
                //保存根节点,其id会自动生成
                teachplanRepository.save(teachplanRoot);
                //利用生成的根节点,设置二级节点的等级
                teachplan.setGrade((Integer.parseInt(teachplanRoot.getGrade())+1)+"");
                teachplan.setParentid(teachplanRoot.getId());
                teachplanRepository.save(teachplan);
            }else{
                Teachplan teachplanRoot = teachplanList.get(0);
                teachplan.setParentid(teachplanRoot.getId());
                teachplan.setGrade((Integer.parseInt(teachplanRoot.getGrade())+1)+"");
                teachplanRepository.save(teachplan);
            }
            return new ResponseResult(CommonCode.SUCCESS);
        }

    }

}
