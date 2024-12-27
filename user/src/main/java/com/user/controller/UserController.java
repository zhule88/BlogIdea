package com.user.controller;


import com.pojo.Result;
import com.user.pojo.message;
import com.user.pojo.register;
import com.user.pojo.user;
import com.user.service.UserService;
import com.user.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Tag(name = "用户管理")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
     StringRedisTemplate redisTemplate;

    @Operation(summary = "登录")
    @PostMapping("/login")
    public Result login(@RequestBody user uuser) {
        user u = userService.lambdaQuery().eq(user::getEmail,uuser.getEmail())
                .eq(user::getPassword,uuser.getPassword()).one();
        if(u==null){
           return Result.error("账号或密码错误");
        }
        return Result.success(jwtUtils.getJWT(u.getId().toString()));
    }


    @Operation(summary = "注册")
    @PostMapping("/register")
    public Result register(@RequestBody register register) {
        String code = redisTemplate.opsForValue().get(register.getCode());
        if(code==null){
            return Result.error("验证码错误");
        }
        user u  = userService.lambdaQuery().eq(user::getEmail,register.getEmail()).one();
        if(u==null){
            return Result.error("邮箱已注册");
        }
        BeanUtils.copyProperties(register, u);
        userService.save(u);
        return Result.success();
    }


    @Operation(summary = "发送验证码")
    @PostMapping("/email/send")
    public Result emailSend(String email) {
        String code = String.valueOf(new Random().nextInt(9000) + 1000);
        redisTemplate.opsForValue().set(email, code, 5, TimeUnit.MINUTES);
        rabbitTemplate.convertAndSend("email","email",
                new message(email,"\uD83D\uDC4F您的注册验证码为" + code+",有效期五分钟"));
        return Result.success();
    }



}
