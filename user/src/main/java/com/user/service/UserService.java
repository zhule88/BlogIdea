package com.user.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.pojo.Result;
import com.user.pojo.auth;
import com.user.pojo.user;

public interface UserService  extends IService<user> {
    Result login( auth auth);
    Result register(auth auth);
}
