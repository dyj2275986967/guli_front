package com.atguigu.educenter.service;

import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author mangoubiubiu
 * @since 2020-11-14
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    //登录
    String login(UcenterMember ucenterMember);
   //注册
    void register(RegisterVo registerVo);
}
