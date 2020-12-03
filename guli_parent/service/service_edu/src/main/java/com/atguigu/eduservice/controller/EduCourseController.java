package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.query.CourseQuery;
import com.atguigu.eduservice.entity.query.TeacherQuery;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-10-09
 */
@RestController
@RequestMapping("/eduservice/edu-course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

   //添加课程的基本信息
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        //返回添加成功之后的课程id
     String cid=   eduCourseService.saveCourseInfoVo(courseInfoVo);

        return  R.ok().data("courseId",cid);
    }
    //查询课程的基本信息
    @GetMapping("getCourseInfo/{courseId}")
    public  R getCourseInfo(@PathVariable String courseId){

      CourseInfoVo courseInfoVo=  eduCourseService.getCourseInfoById(courseId);

      return  R.ok().data("courseInfoVo",courseInfoVo);

    }
    //修改课程信息
    @PostMapping("updataCourseInfo")
    public  R updataCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        eduCourseService.updataCourseInfo(courseInfoVo);


          return R.ok().data("msg","修改课程信息成功");

    }

     //根据课程id查询课程确认的信息
    @GetMapping("getPublishCourseInfo/{courseId}")
    public R getPublishCourseInfo(@PathVariable String courseId){
      CoursePublishVo coursePublishVo= eduCourseService.getPublishCourseInfo(courseId);

       return R.ok().data("list",coursePublishVo);
    }

    //修改课程最终发布
    @PostMapping("publishCourse/{courseId}")
    public R publishCourse(@PathVariable String courseId){
        EduCourse eduCourse=new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);//设置课程最终发布状态
        return R.ok();
    }
    //查询全部课程信息
    @GetMapping("getCourseList")
    public  R  getCourseList(){

      List<EduCourse> eduCourses= eduCourseService.list(null);
        return  R.ok().data("list",eduCourses);
    }
    //条件查询加分页
    @PostMapping("pageCourseCondition/{current}/{limit}")
    public  R pageCourseCondition(@ApiParam(name="current",value = "当前页",required = true) @PathVariable Long current,
                                  @ApiParam(name="limit",value = "记录数",required = true) @PathVariable Long limit,
                                  @ApiParam(name="courseQuery",value = "条件信息",required = false) @RequestBody(required = false)CourseQuery courseQuery){
        //current:当前页，size：每页的记录数
        Page<EduCourse> pageInfo=new Page<>(current,limit);

        //构造查询条件
        QueryWrapper<EduCourse> queryWrapper=new QueryWrapper<>();
        String title=courseQuery.getTitle();
        if(!StringUtils.isEmpty(title)){
            //模糊查询
            queryWrapper.like("title",title);
        }

        String status=courseQuery.getStatus();
        if(!StringUtils.isEmpty(status)){
            String statusName="";
            if("0".equals(status)){
                statusName="Draft";
            }else if("1".equals(status)){
                statusName="Normal";
            }

            //条件查询
            queryWrapper.eq("status",statusName);
        }
         //通过创建时间排序
        queryWrapper.orderByDesc("gmt_create");

        //调用方法时底层会做封装，封装到pageInfo里面
        eduCourseService.page(pageInfo,queryWrapper);

        long total= pageInfo.getTotal();//总记录数
        List<EduCourse> records = pageInfo.getRecords();//所有的数据list

        return R.ok().data("total",total).data("rows",records);

    }

    @DeleteMapping("{courseId}")
    public R removeCourseByCourseId(@PathVariable String courseId){
        eduCourseService.removeByCourseId(courseId);
        return R.ok();
    }

}

