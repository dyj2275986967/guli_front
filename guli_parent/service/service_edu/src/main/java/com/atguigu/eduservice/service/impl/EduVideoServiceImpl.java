package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-10-09
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {



    @Autowired
    private VodClient vodClient;
    @Override
    public void removeByCourseId(String courseId) {

        QueryWrapper<EduVideo> queryWrapper=new QueryWrapper<>();

        queryWrapper.eq("course_id",courseId);
        queryWrapper.select("video_source_id");

        List<EduVideo> videoIdList=  baseMapper.selectList(queryWrapper);
        List<String> listStr=new ArrayList<>();
        for (EduVideo eduVideo:videoIdList
             ) {
            listStr.add(eduVideo.getVideoSourceId()) ;
        }
        if(listStr.size()>0) {
            //根据多个视频id删除视频
            vodClient.deleteBatch(listStr);
        }
        QueryWrapper<EduVideo> queryWrapperVideo=new QueryWrapper<>();
         queryWrapperVideo.eq("course_id",courseId);
         baseMapper.delete(queryWrapperVideo);
    }
}
