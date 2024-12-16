package com.article.service.impl;

import com.article.mapper.ArticleMapper;
import com.article.pojo.article;
import com.article.service.ArticleService;

import com.article.service.ArticleTagService;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.pojo.page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, article> implements ArticleService  {

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    ArticleTagService articleTagService;

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

    @Override
    public page<article> page(int current, int size, int state) {
        Page<article> pa = Page.of(current, size);
        pa.addOrder(new OrderItem("create_time", false));
        lambdaQuery().eq(article::getState,state).page(pa);
        page<article> p = new page<>(pa.getTotal(), pa.getRecords());
        p.setRecords(articleTagService.tag(p.getRecords()));
        return p;
    }


}
