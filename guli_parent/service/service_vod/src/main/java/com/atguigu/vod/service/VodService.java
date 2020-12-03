package com.atguigu.vod.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    public String uploadVideo(MultipartFile file);

    void removeAlyVideo(List videoIdList);
}
