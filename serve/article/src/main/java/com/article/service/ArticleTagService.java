package com.article.service;

import com.article.pojo.article;
import com.article.pojo.articletag;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ArticleTagService extends IService<articletag> {
    article tag(article article) ;
    List<article> tag(List<article> aarticle);
}
