package com.article.service.impl;

import com.article.mapper.ArticleTagMapper;

import com.article.pojo.article;
import com.article.pojo.articletag;

import com.article.service.ArticleTagService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, articletag> implements ArticleTagService {


    public article tag(article article) {
        List<articletag> l = lambdaQuery()
                .eq(articletag::getArticleId,article.getId())
                .list();
        List<Integer> tags = new ArrayList<>();
        for (articletag articletag : l) {
            tags.add(articletag.getTagId());
        }
        article.setTags(tags);
        return article;
    }

    public List<article> tag(List<article> aarticle) {
        List<Integer> l = aarticle.stream()
                .map(article::getId)
                .toList();
        List<articletag> ll =  lambdaQuery()
                .in(articletag::getArticleId,l)
                .list();
        Map<Integer,List<Integer>> m = new HashMap<>();
        for (articletag articletag : ll) {
            List<Integer> lll =  m.getOrDefault(articletag.getArticleId(),new ArrayList<>());
            lll.add(articletag.getTagId());
            m.put(articletag.getArticleId(),lll);
        }
        aarticle.forEach(article -> article.setTags(m.get(article.getId())));
        return aarticle;
    }
}
