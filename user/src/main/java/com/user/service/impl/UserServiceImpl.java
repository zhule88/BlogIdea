package com.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.user.mapper.UserMapper;


import com.user.pojo.user;

import com.user.service.UserService;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, user> implements UserService {

}
