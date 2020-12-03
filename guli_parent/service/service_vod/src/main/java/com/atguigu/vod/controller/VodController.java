package com.atguigu.vod.controller;

import com.aliyun.oss.ClientException;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.exceptionhander.GuiLiException;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.util.ConstantPropertiesUtils;
import com.atguigu.vod.util.InitVodCilent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

        //上传视频到阿里云
     @PostMapping("uploadAlyuVideo")
    public R uploadAlyuVideo(MultipartFile file){

         String videoId=   vodService.uploadVideo(file);
         return  R.ok().data("videoId",videoId);
     }

     //根据视频id删除
    @DeleteMapping("removeAlyVideo/{id}")
    public  R removeAlyVideo(@PathVariable String id){

        try{
            DefaultAcsClient client = InitVodCilent.initVodClient(
                    ConstantPropertiesUtils.KEYID,
                    ConstantPropertiesUtils.KEYSECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);
            DeleteVideoResponse response = client.getAcsResponse(request);
            System.out.print("RequestId = " + response.getRequestId() + "\n");
        }catch (Exception e){
            throw new GuiLiException(20001, "视频删除失败");
        }


         return R.ok();
    }

    //删除多个视频
    @DeleteMapping("delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList){


             vodService.removeAlyVideo(videoIdList);

         return R.ok();

    }
}
