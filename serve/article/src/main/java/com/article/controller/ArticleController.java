package com.article.controller;


import com.article.pojo.article;
import com.article.service.ArticleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pojo.Result;
import com.pojo.page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/article")
@Tag(name = "文章管理")
public class ArticleController {
    @Autowired
    ArticleService articleService;


    @Operation(summary = "分页查询")
    @GetMapping("/page")
    public Result page(int current, int size) {
        Page<article> pa = Page.of(current, size);
        pa.addOrder(new OrderItem("update_time", false));
        // 1.分页查询
        articleService.page(pa);
        page<article> p = new page<>(pa.getTotal(), pa.getRecords());
        // 2.封装并返回
        return Result.success(p);
    }

    @Operation(summary = "查询所有或状态查询")
    @GetMapping("/list")
    public Result list(int state) {
        if (state == 3) {
            return Result.success(articleService.list());
        }
        return Result.success(articleService.list(new LambdaQueryWrapper<article>().eq(article::getState, state)));
    }
    @Operation(summary = "根据id查询")
    @GetMapping("/get")
    public Result get(int id) {
        return Result.success(articleService.getById(id));
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
        articleService.updateById(article);
        return Result.success();
    }
    @Operation(summary = "根据id删除")
    @DeleteMapping("/del")
    public Result del(int id) {
        articleService.removeById(id);
        return Result.success();
    }
    @Operation(summary = "查找最新id")
    @GetMapping("/idGet")
    public Result newIdGet() {
        int id = articleService.newIdget();
        return Result.success(id);
    }
}
