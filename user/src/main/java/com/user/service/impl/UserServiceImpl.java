package com.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.common.pojo.Result;
import com.common.service.MinioService;
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
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

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
    @Autowired
    MinioService minioService ;


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
        u = lambdaQuery().eq(user::getUsername, auth.getUsername()).one();
        if (u != null) {
            return Result.error("用户名重复");
        }
        u = new user();
        BeanUtils.copyProperties(auth, u);
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        u.setCreateTime(LocalDateTime.now());
        userMapper.insert(u);
        return Result.success();
    }

    @Override
    public String avatar(MultipartFile file, String email) throws Exception {
        String url = minioService.addFile(file);
        user u  = lambdaQuery()
                .eq(user::getEmail, email)
                .one();
        if(!u.getAvatar().isEmpty()){
            int lastIndex = u.getAvatar().lastIndexOf("/");
            String fileName = u.getAvatar().substring(lastIndex + 1);
            minioService.del(fileName);
        }
        lambdaUpdate()
                .eq(user::getEmail, email)
                .set(user::getAvatar,url).update();
        return url;
    }
}

