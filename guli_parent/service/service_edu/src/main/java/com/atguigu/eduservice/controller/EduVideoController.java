package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author mangoubiubiu
 * @since 2020-10-09
 */
@RestController
@RequestMapping("/eduservice/edu-video")
@CrossOrigin
public class EduVideoController {

  @Autowired
  private EduVideoService eduVideoService;


    //添加小节
    @PostMapping("addVideo")
    public R   addVideo(@RequestBody EduVideo eduVideo){

        eduVideoService.save(eduVideo);

        return R.ok();
    }

    //根据id查询小结
    @GetMapping("getVideoById/{videoId}")
    public R getVideoById(@PathVariable String videoId){

      EduVideo eduVideo = eduVideoService.getById(videoId);

      return R.ok().data("eduVideo",eduVideo);
    }
    //修改小结
    @PostMapping("updateVideo")
    public R  updateVideo(@RequestBody EduVideo eduVideo){

        eduVideoService.updateById(eduVideo);
       return R.ok();
    }
    //删除小结
    //TODO  删除小结时要把视频也要删掉
    @DeleteMapping("{videoId}")
    public R deleteVideoById(@PathVariable String videoId){
     Boolean flag =   eduVideoService.removeById(videoId);
        if(flag){
          return   R.ok().data("msg","删除成功");

        }else{
          return  R.error();
        }

    }

}

