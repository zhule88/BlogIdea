package com.article.controller;

import com.article.pojo.articletag;
import com.article.service.ArticleTagService;
import com.article.service.TagService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pojo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tag")
@Tag(name = "标签管理")
public class TagController {
    @Autowired
    TagService tagService;
    @Autowired
    ArticleTagService articleTagService;

    @Operation(summary = "查询标签表")
    @GetMapping("/list")
    public Result list(){
        return Result.success(tagService.list());
    }
    @Operation(summary = "查询文章标签")
    @GetMapping("/article/get")
    public Result getArticleTag(int id){
        return Result.success(articleTagService.lambdaQuery()
                .eq(articletag::getArticleId,id)
                .select(articletag::getTagId)
                .list()
                .stream()
                .map(articletag::getTagId)
                .collect(Collectors.toList()));
    }

    @Operation(summary = "文章添加标签")
    @PostMapping("/article/add")
    public Result addArticleTag(List<Integer> ids,@RequestParam(value = "id") int articleId){
        List<articletag> l = new ArrayList<>();
        for (int tagId:ids){
            articletag t = new articletag();
            t.setArticleId(articleId);
            t.setTagId(tagId);
            l.add(t);
        }
        articleTagService.saveBatch(l);
        return Result.success();
    }
}
