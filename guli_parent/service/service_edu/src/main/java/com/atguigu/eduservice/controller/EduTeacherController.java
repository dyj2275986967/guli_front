package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.servicebase.exceptionhander.GuiLiException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-09-22
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/edu-teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService service;

    //rest风格

    /**
     * 查询所有的教师信息
     * @return
     */
    @ApiOperation(value = "所有的教师信息")
    @GetMapping("findAll")
    public R findAllTeacher(){
        List<EduTeacher>  list= service.list(null);
        return R.ok().data("items",list);
    }

    /**
     * @DeleteMapping("{id}") 通过路径传递
     * @PathVariable String id 得到路径中的id值
     * @param id
     * @return
     */
    @ApiOperation(value = "逻辑删除接口")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name="id",value = "讲师ID",required = true) @PathVariable String id){
        boolean flag=service.removeById(id);
        if(flag==true){
            return R.ok();
        }else {
            return R.error();
        }
    }


    /**
     *分页
     * @param current 当前页
     * @param limit 记录数
     * @return
     */
    @ApiOperation(value = "查询教师信息分页接口")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@ApiParam(name="current",value = "当前页",required = true) @PathVariable Long current,
                             @ApiParam(name="limit",value = "记录数",required = true) @PathVariable Long limit){
        //current:当前页，size：每页的记录数
        Page<EduTeacher> pageInfo=new Page<>(current,limit);

        //调用方法时底层会做封装，封装到pageInfo里面
        service.page(pageInfo,null);
        long total= pageInfo.getTotal();//总记录数
        List<EduTeacher> records = pageInfo.getRecords();//所有的数据list

        return R.ok().data("total",total).data("rows",records);

    }

    /**
     *  @RequestBody TeacherQuery teacherQuery  用@requestBody  他把前端带过来的json封装成一个对象，但是就不能用get提交，get取不到
     *  要改成post
     *  required =false  表示参数值可以为空
     * @param current
     * @param limit
     * @param teacherQuery
     * @return
     */
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageListTeacherCondition(@ApiParam(name="current",value = "当前页",required = true) @PathVariable Long current,
                                      @ApiParam(name="limit",value = "记录数",required = true) @PathVariable Long limit,
                                      @ApiParam(name="TeacherQuery",value = "条件信息",required = false) @RequestBody(required = false) TeacherQuery teacherQuery){
        //current:当前页，size：每页的记录数
        Page<EduTeacher> pageInfo=new Page<>(current,limit);

        //构建条件
        QueryWrapper<EduTeacher> queryQueryWrapper=new QueryWrapper<>();
        //多条件组合查询，和Mybatis provider一样，动态sql吗
        String name=  teacherQuery.getName();
        Integer level=  teacherQuery.getLevel();
        String begin=  teacherQuery.getBegin();
        String end=  teacherQuery.getEnd();
        if(!StringUtils.isEmpty(name)){
            //都是表中的字段名 column
            queryQueryWrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            //都是表中的字段名 column
            queryQueryWrapper.eq("level",level);
        }

        if(!StringUtils.isEmpty(begin)){
            //大于开始时间：ge   里面传表中字段名
            queryQueryWrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            //小于结束时间：le 里面传表中字段名
            queryQueryWrapper.le("gmt_create",end);
        }
        //通过时间排序
        queryQueryWrapper.orderByDesc("gmt_create");

        //调用方法时底层会做封装，封装到pageInfo里面
        service.page(pageInfo,queryQueryWrapper);
        long total= pageInfo.getTotal();//总记录数
        List<EduTeacher> records = pageInfo.getRecords();//所有的数据list

        return R.ok().data("total",total).data("rows",records);

    }

    /**
     * 添加教师接口
     * @param eduTeacher
     * @return
     */
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
       boolean flag=  service.save(eduTeacher);
        if(flag==true){
            return R.ok();
        }else {
            return R.error();
        }
    }

    // 根据讲师id查询讲师信息
    @GetMapping("getTeacher/{id}")
    public  R getTeacher(@PathVariable String id){
        EduTeacher eduTeacher = service.getById(id);
        return R.ok().data("teacher",eduTeacher);

    }
    //讲师修改功能
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
       boolean flag= service.updateById(eduTeacher);
        if(flag==true){
            return R.ok();
        }else {
            return R.error();
        }
    }


}

