package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhander.GuiLiException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;
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

    @Override
    public CoursePublishVo getPublishCourseInfo(String courseId) {
        return baseMapper.getPublishCourseInfo(courseId);
    }

    @Override
    public void removeByCourseId(String courseId) {

        //根据课程id删除小节
        eduVideoService.removeByCourseId(courseId);
        //根据课程id删除章节
        eduChapterService.removeByCourseId(courseId);
        //根据课程id删除描述信息
        eduCourseDescriptionService.removeById(courseId);

        //根据课程id删除课程
      int result=  baseMapper.deleteById(courseId);
        if(result==0){
            throw new GuiLiException(20001,"删除失败");
        }

    }
}
