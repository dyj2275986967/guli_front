package com.atguigu.educenter.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author mangoubiubiu
 * @since 2020-11-14
 */
@RestController
@RequestMapping("/educenter/ucenter-member")
@CrossOrigin
public class UcenterMemberController {

     @Autowired
    private UcenterMemberService memberService;



     //登录
      @PostMapping("login")
      public R login(@RequestBody UcenterMember ucenterMember){

          //调用service方法实现登录
          //返回token值 使用jwt生成
          String token= memberService.login(ucenterMember);


          return  R.ok().data("token",token);

      }


     //注册
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){

         memberService.register(registerVo);

         return R.ok();
    }
    //根据token获取用户信息
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){

        String menberId=JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库 根据用户id获取用户信息
        UcenterMember member=    memberService.getById(menberId);
         return R.ok().data("member",member);
    }







}

