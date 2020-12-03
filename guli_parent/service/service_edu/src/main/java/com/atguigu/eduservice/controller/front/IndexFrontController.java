package com.atguigu.eduservice.controller.front;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/eduservice/index-front")
@CrossOrigin
public class IndexFrontController {
    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduTeacherService service;

       //查询前8条热门课程  前4个名师

    @GetMapping("index")
    public R index(){

        QueryWrapper<EduCourse> queryWrapper=new QueryWrapper<>();

        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit 8");

        List<EduCourse> eduCoursesList=eduCourseService.list(queryWrapper);

        QueryWrapper<EduTeacher> teacherWrapper=new QueryWrapper<>();

        teacherWrapper.orderByDesc("id");
        teacherWrapper.last("limit 4");

        List<EduTeacher> eduTeacherList=service.list(teacherWrapper);

        return R.ok().data("eduList",eduCoursesList).data("teacherList",eduTeacherList);
    }



}
