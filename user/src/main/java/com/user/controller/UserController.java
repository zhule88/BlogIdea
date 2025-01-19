package com.user.controller;


import com.common.pojo.Result;
import com.user.pojo.message;
import com.user.pojo.auth;
import com.user.pojo.user;
import com.user.service.UserService;
import com.user.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Tag(name = "用户管理")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    StringRedisTemplate redisTemplate;


    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Operation(summary = "登录")
    @PostMapping("/login")
    public Result login(@RequestBody auth auth) {
       return userService.login(auth);
    }

    @Operation(summary = "注册")
    @PostMapping("/register")
    public Result register(@RequestBody auth auth) {
        return  userService.register(auth);
    }


    @Operation(summary = "获取用户信息")
    @GetMapping("/userInfo")
    public Result userInfo(@RequestHeader( "Authorization") String token) {
        return  Result.success(userService.getById(jwtUtils.parseJWT(token)));
    }



    @Operation(summary = "重置密码")
    @PostMapping("/reset")
    public Result reset(@RequestBody auth auth) {
        String code = redisTemplate.opsForValue().get("code:"+auth.getEmail());
        if (!auth.getCode().equals(code)) {
            return Result.error("验证码错误");
        }
        userService.lambdaUpdate()
                .eq(user::getEmail, auth.getEmail())
                .set(user::getPassword, passwordEncoder.encode(auth.getPassword()))
                .update();
        return Result.success();
    }

    @Operation(summary = "上传头像")
    @PostMapping("/avatar")
    public Result avatar(@RequestParam("file") MultipartFile file,String email) throws Exception {
        return Result.success( userService.avatar(file,email));
    }

    @Operation(summary = "发送验证码")
    @GetMapping("/email")
    public Result emailSend(String email) {
        String code = String.valueOf(new Random().nextInt(9000) + 1000);
        redisTemplate.opsForValue().set("code:"+email, code, 10, TimeUnit.MINUTES);
        rabbitTemplate.convertAndSend("email","email",
                new message(email,"\uD83D\uDC4F 您的注册验证码为" + code+",十分钟内有效"));
        return Result.success();
    }
}
