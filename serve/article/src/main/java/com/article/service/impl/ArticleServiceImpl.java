package com.article.service.impl;

import com.article.mapper.ArticleMapper;
import com.article.pojo.article;
import com.article.service.ArticleService;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, article> implements ArticleService  {

    @Autowired
    ArticleMapper articleMapper;

    public List<article> list(int state ,int top){
        if (state == 3 && top == 3) {
            return lambdaQuery()
                    .list();
        }
        if(state == 3){
            return lambdaQuery()
                    .eq(article::getTop, top)
                    .list();
        }
        if(top == 3) {
            return lambdaQuery()
                    .eq(article::getState, state)
                    .list();
        }
        return lambdaQuery()
                .eq(article::getState, state)
                .eq(article::getTop, top)
                .list();
    }
}
