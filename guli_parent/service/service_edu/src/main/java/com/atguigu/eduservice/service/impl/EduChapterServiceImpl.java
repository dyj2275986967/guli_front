package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhander.GuiLiException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-10-09
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
     private EduVideoService eduVideoService;



    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //根据课程id查询章节
        QueryWrapper<EduChapter> queryChapterVoWrapper =new QueryWrapper<>();
        queryChapterVoWrapper.eq("course_id",courseId);
        List<EduChapter> eduChapterList=baseMapper.selectList(queryChapterVoWrapper);



        //根据课程id查小结
        QueryWrapper<EduVideo> eduVideoQueryWrapper =new QueryWrapper<>();
        eduVideoQueryWrapper.eq("course_id",courseId);
        List<EduVideo> eduVideoList=eduVideoService.list(eduVideoQueryWrapper);



        //遍历存章节
        List<ChapterVo> chapterVoList =new ArrayList<>();
        for (int i = 0; i < eduChapterList.size(); i++) {
            ChapterVo chapterVo=new ChapterVo();
            BeanUtils.copyProperties(eduChapterList.get(i),chapterVo);
            List<VideoVo> videoVoList=new ArrayList<>();
            //遍历存小结
            for (int j = 0; j <eduVideoList.size() ; j++) {

                if(eduChapterList.get(i).getId().equals(eduVideoList.get(j).getChapterId())){
                    VideoVo videoVo=new VideoVo();
                    BeanUtils.copyProperties(eduVideoList.get(j),videoVo);
                    videoVoList.add(videoVo);
                }

            }
            chapterVo.setChildren(videoVoList);
            chapterVoList.add(chapterVo);

        }



        return chapterVoList;
    }

    @Override
    public Boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> queryWrapper=new QueryWrapper<>();
         queryWrapper.eq("chapter_id",chapterId);

      int count= eduVideoService.count(queryWrapper);

       if(count>0 ){
            throw new GuiLiException(20001,"不能删除");
       }else {
         int result=  baseMapper.deleteById(chapterId);
         //成功  是 1  1>0 是true  0>0 是false
         return result>0;
       }

    }

    @Override
    public void removeByCourseId(String courseId) {
        QueryWrapper<EduChapter> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        baseMapper.delete(queryWrapper);
    }
}
