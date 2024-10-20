package com.article.service.impl;

import com.article.mapper.ArticleMapper;
import com.article.pojo.article;
import com.article.service.ArticleService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, article> implements ArticleService  {



}
