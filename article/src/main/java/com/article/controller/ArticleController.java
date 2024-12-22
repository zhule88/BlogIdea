package com.article.controller;


import com.article.pojo.article;
import com.article.pojo.articletag;
import com.article.service.ArticleService;
import com.article.service.ArticleTagService;
import com.pojo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@RestController
@RequestMapping("/article")
@Tag(name = "文章管理")
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @Autowired
    ArticleTagService articleTagService;

    @Operation(summary = "分页查询")
    @GetMapping("/page")
    public Result page(int current, int size,int state) {
        return Result.success(articleService.page(current, size, state));
    }

    @Operation(summary = "根据状态或置顶查询")
    @GetMapping("/list")
    public Result list(  @RequestParam(required = false,defaultValue = "3")int state,
                         @RequestParam(required = false,defaultValue = "3")int top) {
        return Result.success(articleTagService.tag(articleService.list(state,top)));

    }
    @Operation(summary = "根据id查询")
    @GetMapping("/get")
    public Result get(int id) {
        return Result.success(articleTagService.tag(articleService.getById(id)));
    }

    @Operation(summary = "根据id集合查询")
    @PostMapping("/list/byIds")
    public Result getByIds( @RequestBody List<Integer> ids) {
        return Result.success(articleTagService.tag(articleService.listByIds(ids)));
    }

    @Operation(summary = "根据分类id查询")
    @GetMapping("/list/byCateId")
    public Result getArticleByCateId(Integer id){
        return Result.success( articleService.lambdaQuery()
                .eq(article::getCategoryId,id)
                .list());
    }
    @Operation(summary = "根据标签id查询")
    @GetMapping("/list/byTagId")
    public Result getArticleByTagId(Integer id){
        List<articletag> l  =articleTagService.lambdaQuery()
                .eq(articletag::getTagId,id)
                .list();
        List<Integer> ll = l.stream().map(articletag::getArticleId).toList();
        return Result.success(articleService.listByIds(ll));
    }

    @Operation(summary = "新增文章")
    @PostMapping("/add")
    public Result add(@RequestBody article article) {
        article.setVisitCount(0);
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());

        articleService.save(article);

        return Result.success();
    }
    @Operation(summary = "修改文章")
    @PutMapping("/update")
    public Result update(@RequestBody article article) {
        article.setUpdateTime(LocalDateTime.now());
        articleTagService.lambdaUpdate().eq(articletag::getArticleId,article.getId()).remove();
        List<articletag> l = new ArrayList<>();
        for (Integer i: article.getTags()) {
            l.add(new articletag(article.getId(),i));
        }
        articleTagService.saveBatch(l);
        articleService.updateById(article);
        return Result.success();
    }



    @Operation(summary = "根据id删除")
    @DeleteMapping("/del")
    public Result del(int id) {
        articleTagService.lambdaUpdate().eq(articletag::getArticleId,id).remove();
        articleService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "查询分类对应文章数")
    @GetMapping("/count")
    public Result count(int id){
        return Result.success(articleService.lambdaQuery().eq(article::getCategoryId, id).count());
    }

    @Operation(summary = "查询文章上下篇")
    @GetMapping("/around")
    public Result around(int id){
        List<article> res = new ArrayList<>();
        res.add(articleService.lambdaQuery().lt(article::getId,id).eq(article::getState,2).orderByDesc(article::getId) .last("LIMIT 1").one());
        res.add(articleService.lambdaQuery().gt(article::getId,id).eq(article::getState,2).last("LIMIT 1").one());
        return Result.success(res);
    }

}
