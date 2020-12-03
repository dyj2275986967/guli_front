package com.atguigu.educms.controller;

import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 前台banner显示
 */
@RestController
@RequestMapping("/educms/crm-banner-front")
@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService crmBannerService;

    //查询所有的banner
    @GetMapping("getAllBanner")
    public R getAllBanner(){
     List<CrmBanner> crmBannerList=crmBannerService.selectAllBanner();

        return  R.ok().data("list",crmBannerList);
    }

}
