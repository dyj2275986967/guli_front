package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/eduservice/edu-chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    //根据课程id查章节小结
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
              List<ChapterVo> chapterVoList =eduChapterService.getChapterVideoByCourseId(courseId);
        return  R.ok().data("chapterVoList",chapterVoList);
    }
    //添加章节
    @PostMapping("addChapter")
    public  R addChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.save(eduChapter);
       return  R.ok();
    }

    //根据id查询章节
    @GetMapping("getChapterInfo/{chapterId}")
    public  R getChapterInfoByChapterId(@PathVariable String chapterId){

       EduChapter chapter= eduChapterService.getById(chapterId);
        return  R.ok().data("chapter",chapter);
    }


    //修改章节
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){

        eduChapterService.updateById(eduChapter);

        return R.ok();
    }

    //删除章节
    @DeleteMapping("{chapterId}")
    public  R deleteChapterByChapterId(@PathVariable String chapterId){
      Boolean flag=     eduChapterService.deleteChapter(chapterId);
        if(flag){
            return  R.ok().data("msg","删除成功");
        }else{
            return  R.error().data("msg","删除失败");
        }
    }



}

