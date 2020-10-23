package com.atguigu.oss.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface OssService {

    //上次头像到oss
    String uploadFileAvatar(MultipartFile file);
}
