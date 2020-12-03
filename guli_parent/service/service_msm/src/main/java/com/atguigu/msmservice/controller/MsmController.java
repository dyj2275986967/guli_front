package com.atguigu.msmservice.controller;


import com.atguigu.msmservice.service.MsmService;
import com.atguigu.commonutils.R;
import com.atguigu.msmservice.utils.RandomUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @GetMapping("send/{phone}")
    public R sendPhoneMsg(@PathVariable String phone){

        String code=redisTemplate.opsForValue().get(phone);

        //如果从redis 取值 不为空 代表已经发生短信
        if(!StringUtils.isEmpty(code)){
            return R.ok();
        }


        code= RandomUtil.getFourBitRandom();

        Map<String,Object> param=new HashMap<String,Object>();
        param.put("code",code);

        //调用service 发送短信的方法
        Boolean isSend=   msmService.send(param,phone);
         if(isSend){
             //发送成功 后 发验证码 放到 redis 设置 有效时间  key  value   5     分钟
             redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
             return R.ok();
         }else{
             return R.error().message("短信发送失败");
         }
    }


}
