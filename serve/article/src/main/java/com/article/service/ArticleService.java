package com.article.service;

import com.article.pojo.article;
import com.baomidou.mybatisplus.extension.service.IService;



public interface ArticleService extends IService<article> {
    int newIdget();
}
