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

    @Operation(summary = "根据文章id查询标签")
    @GetMapping("/articleTag/get")
    public Result getArticleTag(Integer id){
        return Result.success(articleTagService.lambdaQuery()
                .eq(articletag::getArticleId,id)
                .select(articletag::getTagId)
                .list()
                .stream()
                .map(articletag::getTagId)
                .collect(Collectors.toList()));
    }
    @Operation(summary = "根据标签id查询文章")
    @GetMapping("/article/list")
    public Result getArticle(Integer id){
        return Result.success( articleTagService.lambdaQuery()
                .eq(articletag::getTagId,id)
                .select(articletag::getArticleId)
                .list()

                .stream()
                .map(articletag::getArticleId)
                .collect(Collectors.toList()));
    }

    @Operation(summary = "文章添加标签")
    @PostMapping("/articleTag/add")
    public Result addArticleTag(@RequestBody List<articletag> articletaglist){
        articleTagService.remove(new LambdaQueryWrapper<articletag>()
                .eq(articletag::getArticleId,articletaglist.get(0).getArticleId()));
        articleTagService.saveBatch(articletaglist);
        return Result.success();
    }

    @Operation(summary = "文章删除标签")
    @DeleteMapping("/articleTag/del")
    public Result delArticleTag(int id){
        articleTagService.remove(new LambdaQueryWrapper<articletag>()
                .eq(articletag::getArticleId,id));
        return Result.success();
    }
}
