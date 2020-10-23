package com.atguigu.oss.service.serviceimpl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.util.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        String endpoint = ConstantPropertiesUtils.EDNPOINT;
        String accessKeyId = ConstantPropertiesUtils.KEYID;
        String accessKeySecret = ConstantPropertiesUtils.KEYSECRET;
        String bucketName=ConstantPropertiesUtils.BUCKETNAME;
        try{
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            //获取文件名称
            String fileName=file.getOriginalFilename();

            //在文件中添加随机值 uuid
            String uuid=   UUID.randomUUID().toString().replaceAll("-","");
            fileName=uuid+fileName;

            //获取当前日期
            String dataPath=  new DateTime().toString("yyyy/MM/dd");

            fileName=dataPath+"/"+fileName;

            ossClient.putObject(bucketName, fileName, inputStream);
//
            // 关闭OSSClient。
            ossClient.shutdown();
          String url="https://"+bucketName+"."+endpoint+"/"+fileName;
            return  url;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
