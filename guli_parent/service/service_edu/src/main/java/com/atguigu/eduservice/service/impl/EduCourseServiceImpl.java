package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.servicebase.exceptionhander.GuiLiException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-10-09
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Override
    public String saveCourseInfoVo(CourseInfoVo courseInfoVo) {
        //1,向课程表中添加数据
        EduCourse eduCourse=new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
       //影响行数，表示
        int insert=   baseMapper.insert(eduCourse);
         if(insert<=0){
             throw  new GuiLiException(20001,"添加课程失败");
         }

         //得到课程添加之后的id
        String cid= eduCourse.getId();

        //2,向课程描述表中添加数据
        EduCourseDescription eduCourseDescription=new EduCourseDescription();
         BeanUtils.copyProperties(courseInfoVo,eduCourseDescription);
         //设置描述iD就是课程id
        eduCourseDescription.setId(cid);
         eduCourseDescriptionService.save(eduCourseDescription);
         return cid;
    }

    @Override
    public CourseInfoVo getCourseInfoById(String courseId) {
        //查询课程信息
        EduCourse eduCourse=  baseMapper.selectById(courseId);

        CourseInfoVo courseInfoVo=new CourseInfoVo();
        //复制课程信息
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        //查询描述信息
       EduCourseDescription eduCourseDescription= eduCourseDescriptionService.getById(courseId);
        //复制描述信息
        BeanUtils.copyProperties(eduCourseDescription,courseInfoVo);

        return courseInfoVo;
    }

    @Override
    public void updataCourseInfo(CourseInfoVo courseInfoVo) {
        //修改课程信息
        EduCourse course=new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,course);

        int updateCourse =baseMapper.updateById(course);

        if(updateCourse==0){
            throw new GuiLiException(20001,"更新课程信息失败");
        }
        //修改描述信息
        EduCourseDescription eduCourseDescription=new EduCourseDescription();

        BeanUtils.copyProperties(courseInfoVo,eduCourseDescription);

        eduCourseDescriptionService.updateById(eduCourseDescription);

    }
}
