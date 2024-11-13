package com.article.service.impl;

import com.article.mapper.ArticleTagMapper;

import com.article.pojo.articletag;

import com.article.service.ArticleTagService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, articletag> implements ArticleTagService {
}
