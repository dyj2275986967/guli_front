package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectDate;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exceptionhander.GuiLiException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class SubjectExcelListener extends AnalysisEventListener<SubjectDate> {

    //因为 SubjectExcelListener不能交给spring进行管理，需要自己new,不能注入其他对象
    //不能实现数据库操作，所以手动传过来
     private EduSubjectService eduSubjectService;
    //无参构造
    public SubjectExcelListener(){ }
     //有参构造
     public SubjectExcelListener(EduSubjectService eduSubjectService){
         this.eduSubjectService =eduSubjectService;
     }


    @Override
    public void invoke(SubjectDate subjectDate, AnalysisContext analysisContext) {
            if(subjectDate==null){
                throw  new GuiLiException(20001,"文件数据为空");
            }

            //判断一级分类在数据库里面有无
            EduSubject oneEduSubJect =this.exitOneSubject(eduSubjectService,subjectDate.getOneSubjectName());

        //添加一级分类
            if(oneEduSubJect==null){
                oneEduSubJect=new EduSubject();
                oneEduSubJect.setParentId("0");
                oneEduSubJect.setTitle(subjectDate.getOneSubjectName());
                System.out.println(oneEduSubJect);
                System.out.println("=================");
                 eduSubjectService.save(oneEduSubJect);

            }
        //取一级分类的值，做完添加操作之后，自动生成的id值会注入进来
        String pId=oneEduSubJect.getId();

        //判断二级分类在数据库里面有无
          EduSubject twoEduSubJect =this.exitTwoSubject(eduSubjectService,subjectDate.getTwoSubjectName(),pId);
        //添加二级分类

        if(twoEduSubJect==null){
            twoEduSubJect=new EduSubject();
            twoEduSubJect.setParentId(pId);
            twoEduSubJect.setTitle(subjectDate.getTwoSubjectName());
            eduSubjectService.save(twoEduSubJect);
        }
    }

    //判断一级分类不能重复添加，是要从数据库里判断一级分类有没有
    private EduSubject exitOneSubject( EduSubjectService eduSubjectService,String name){

        QueryWrapper<EduSubject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject subject=   eduSubjectService.getOne(wrapper);

        return  subject;
    }

    //判断二级分类不能重复添加
    private EduSubject exitTwoSubject( EduSubjectService eduSubjectService,String name,String Eid){

        QueryWrapper<EduSubject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",Eid);
        EduSubject subject=   eduSubjectService.getOne(wrapper);

        return  subject;
    }

    //    //读取完成后执行的方法
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
