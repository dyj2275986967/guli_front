package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-10-08
 */
@RestController
@RequestMapping("/eduservice/edu-subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;


    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){

         eduSubjectService.saveSubject(file,eduSubjectService);

    //这里要改
     return R.ok();
    }

    @GetMapping("getAllSubject")
    public R getAllSubject(){
     List<OneSubject> list=   eduSubjectService.getAllOneTwoSubject();

        return  R.ok().data("list",list);
    }



}

