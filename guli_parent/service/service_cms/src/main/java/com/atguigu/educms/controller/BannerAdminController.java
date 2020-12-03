package com.atguigu.educms.controller;


import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author mangoubiubiu
 * @since 2020-11-05
 */
@RestController
@RequestMapping("/educms/crm-banner-admin")
public class BannerAdminController {

    @Autowired
    private CrmBannerService crmBannerService;


    //分页查询
    @GetMapping("pagebanner/{page}/{limit}")
    public R pagebanner(@PathVariable long page, @PathVariable long limit){
        Page<CrmBanner> crmBannerPage=new Page<>(page,limit);
        crmBannerService.page(crmBannerPage,null);
        return R.ok().data("items",crmBannerPage.getRecords()).data("totals",crmBannerPage.getTotal());
    }
    //根据id查询
    @GetMapping("get/{id}")
    public R getById(@PathVariable String id){
        crmBannerService.getById(id);
        return R.ok();
    }

    //添加banner
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){

        crmBannerService.save(crmBanner);
        return  R.ok();
    }

    //修改banner
    @PutMapping("update")
    public R update(@RequestBody CrmBanner crmBanner){

        crmBannerService.updateById(crmBanner);
        return  R.ok();
    }

    //删除banner
    @PutMapping("remove/{id}")
    public R delete(@PathVariable String id){

        crmBannerService.removeById(id);
        return  R.ok();
    }
}

