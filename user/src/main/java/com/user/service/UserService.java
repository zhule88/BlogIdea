package com.user.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.common.pojo.Result;
import com.user.pojo.auth;
import com.user.pojo.user;
import org.springframework.web.multipart.MultipartFile;

public interface UserService  extends IService<user> {
    Result login( auth auth);
    Result register(auth auth);
    String  avatar(MultipartFile file, String email) throws Exception;
    void codeSend(String email);
}
