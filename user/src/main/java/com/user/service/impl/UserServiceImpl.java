package com.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.pojo.Result;
import com.user.mapper.UserMapper;


import com.user.pojo.auth;
import com.user.pojo.user;

import com.user.service.UserService;

import com.user.utils.JwtUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, user> implements UserService {

    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtils jwtUtils;
    public Result login(auth auth){
        if(auth.getCode().isEmpty()){
            user u = lambdaQuery().eq(user::getUsername,auth.getUsername())
                    .one();
            if( u == null){
                return Result.error("用户不存在");
            }
            if(!passwordEncoder.matches(auth.getPassword(), u.getPassword() )){
                return Result.error("账号或密码错误");
            }
            return Result.success(jwtUtils.getJWT(u.getId().toString()));
        }else{

            user u = lambdaQuery().eq(user::getEmail,auth.getEmail())
                    .one();
            if( u == null){
                return Result.error("用户不存在");
            }
            String code = redisTemplate.opsForValue().get("code:"+auth.getEmail());
            if (!auth.getCode().equals(code)) {
                return Result.error("验证码错误");
            }
            return Result.success(jwtUtils.getJWT(u.getId().toString()));
        }
    }

    @Override
    public Result register(auth auth) {
        String code = redisTemplate.opsForValue().get("code:"+auth.getEmail());
        if (!auth.getCode().equals(code)) {
            return Result.error("验证码错误");
        }
        user u = lambdaQuery().eq(user::getEmail, auth.getEmail()).one();
        if (u != null) {
            return Result.error("邮箱已注册");
        }
        u = new user();
        BeanUtils.copyProperties(auth, u);
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        u.setCreateTime(LocalDateTime.now());
        userMapper.insert(u);
        return Result.success();
    }
}

