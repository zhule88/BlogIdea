package com.article.controller;

import com.article.pojo.article;
import com.article.pojo.category;
import com.article.service.ArticleService;
import com.article.service.CategoryService;
import com.pojo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
@Tag(name = "文章分类管理")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ArticleService articleService;
    @Operation(summary = "查询分类表")
    @GetMapping("/list")
    public Result list(){
        return Result.success(categoryService.list());
    }

    @Operation(summary = "查询分类对应文章数")
    @GetMapping("/count")
    public Result count(){
        List<Map<Integer,Long>> l = new ArrayList<>();
        List<category> ll = categoryService.list();
        for (category c : ll){
            Map<Integer,Long> m = new HashMap<>();
            m.put(c.getId(),articleService.lambdaQuery().eq(article::getCategoryId, c.getId()).count());
            l.add(m);
        }
        return Result.success(l);
    }

}
