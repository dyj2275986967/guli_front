package com.atguigu.educenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.servicebase.exceptionhander.GuiLiException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author mangoubiubiu
 * @since 2020-11-14
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @Override
    public String login(UcenterMember ucenterMember) {
        //获取登录手机号和密码
        String mobile=ucenterMember.getMobile();
        String pwd=ucenterMember.getPassword();

        //手机号和密码非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(pwd)){
            throw new GuiLiException(20001,"登录失败");
        }


        //判断手机号是否正确
        QueryWrapper<UcenterMember> queryWrapper=new QueryWrapper<>();

        queryWrapper.eq("mobile",mobile);
        //根据手机号查这个数据 是否存在
        UcenterMember mMember  = baseMapper.selectOne(queryWrapper);
        //判断查出来对象是否为空
        if(mMember==null){
            throw new GuiLiException(20001,"该用户不存在");
        }

        //判断密码是否正确
        //如果密码不正确
        //存到数据库里的密码是做了 MD5加密了的
        //所以用户输人的密码 先要进行加密在来和数据库里的比对
        if(!MD5.encrypt(pwd).equals(mMember.getPassword())){
            throw new GuiLiException(20001,"密码错误");
        }

        //判断用户是否被禁用
         if(mMember.getIsDisabled()){
             throw new GuiLiException(20001,"登录失败");
         }

         //登录成功
         //生成token字符串 使用工具类
        String jwtToken= JwtUtils.getJwtToken(mMember.getId(),mMember.getNickname());

         return jwtToken;
    }

    @Override
    public void register(RegisterVo registerVo) {

        //获取注册的code
        String  code =registerVo.getCode();
        String   mobile    =registerVo.getMobile();
        String   nickname   =registerVo.getNickname();
        String   pwd   =registerVo.getPassword();



        //非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(pwd) || StringUtils.isEmpty(code)  || StringUtils.isEmpty(nickname)){
            throw new GuiLiException(20001,"登录失败");
        }

        //判断code
        //从redis取验证码
        String redisCode=redisTemplate.opsForValue().get(mobile);

        if(!code.equals(redisCode)){
            throw new GuiLiException(20001,"注册失败");
        }

        //判断手机号是否重复
        QueryWrapper<UcenterMember> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("mobile",mobile);
        Integer count= baseMapper.selectCount(queryWrapper);
        //表示有相同
        if(count >0 ){
            throw new GuiLiException(20001,"注册失败");
        }

        UcenterMember member=new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(pwd));
        member.setAvatar("");
        member.setIsDeleted(false);
        baseMapper.insert(member);


    }
}
