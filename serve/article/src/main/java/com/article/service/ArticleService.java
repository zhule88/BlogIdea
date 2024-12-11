package com.article.service;

import com.article.pojo.article;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface ArticleService extends IService<article> {
    List<article> list(int state,int top);
}
