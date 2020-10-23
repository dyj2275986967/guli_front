package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}

