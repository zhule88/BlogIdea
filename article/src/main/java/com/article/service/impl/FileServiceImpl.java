package com.article.service.impl;


import com.article.mapper.FileMapper;

import com.article.pojo.file;

import com.article.service.FileService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, file> implements FileService {

}
