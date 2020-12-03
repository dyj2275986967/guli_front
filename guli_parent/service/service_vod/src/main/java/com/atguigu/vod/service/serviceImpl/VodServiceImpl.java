package com.atguigu.vod.service.serviceImpl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.atguigu.servicebase.exceptionhander.GuiLiException;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.util.ConstantPropertiesUtils;
import com.atguigu.vod.util.InitVodCilent;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {
    @Override
    public String uploadVideo(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));
            UploadStreamRequest request = new UploadStreamRequest(
                    ConstantPropertiesUtils.KEYID,
                    ConstantPropertiesUtils.KEYSECRET,
                    title, originalFilename, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
            // 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            String videoId = response.getVideoId();
            if (!response.isSuccess()) {
                String errorMessage = "阿里云上传错误：" + "code：" + response.getCode() + ", message：" + response.getMessage();
                if(StringUtils.isEmpty(videoId)){
                    throw new GuiLiException(20001, errorMessage);
                }
            }
            return videoId;
        } catch (IOException e) {
            throw new GuiLiException(20001, "guli vod 服务上传失败");
        }
    }

    @Override
    public void removeAlyVideo(List videoIdList) {
        try{
            DefaultAcsClient client = InitVodCilent.initVodClient(
                    ConstantPropertiesUtils.KEYID,
                    ConstantPropertiesUtils.KEYSECRET);
            //先将list转换成数组，然后利用工具类将其拼接
            String str= org.apache.commons.lang.StringUtils.join(videoIdList.toArray(),",");
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(str);
            DeleteVideoResponse response = client.getAcsResponse(request);
            System.out.print("RequestId = " + response.getRequestId() + "\n");
        }catch (Exception e){
            throw new GuiLiException(20001, "视频删除失败");
        }

    }
}
