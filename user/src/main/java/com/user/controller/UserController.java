package com.user.controller;


import com.pojo.Result;
import com.service.MinioService;
import com.user.pojo.message;
import com.user.pojo.auth;
import com.user.pojo.user;
import com.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    MinioService minioService = new MinioService("user");

    @Operation(summary = "登录")
    @PostMapping("/login")
    public Result login(@RequestBody user user) {
       return userService.login(user);
    }


    @Operation(summary = "注册")
    @PostMapping("/register")
    public Result register(@RequestBody auth auth) {
        return  userService.register(auth);
    }
    @Operation(summary = "上传头像")
    @PostMapping("/avatar")
    public Result avatar(@RequestParam("file") MultipartFile file,String email) throws Exception {
        String filename = minioService.addFile(file);
        userService.lambdaUpdate()
                .eq(user::getEmail, email)
                .set(user::getAvatar,filename).update();
        return Result.success();
    }

    @Operation(summary = "发送验证码")
    @GetMapping("/email")
    public Result emailSend(String email) {
        String code = String.valueOf(new Random().nextInt(9000) + 1000);
        redisTemplate.opsForValue().set("code:"+email, code, 5, TimeUnit.MINUTES);
        rabbitTemplate.convertAndSend("email","email",
                new message(email,"\uD83D\uDC4F您的注册验证码为" + code+",五分钟内有效"));
        return Result.success();
    }



}
