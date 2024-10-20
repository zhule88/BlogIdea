package com.article.controller;


import com.article.pojo.article;
import com.article.service.ArticleService;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pojo.Result;
import com.pojo.page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
@Tag(name = "文章接口")

public class ArticleController {
    @Autowired
    ArticleService articleService;


    @Operation(summary = "分页查询")
    @GetMapping("/list")
    public Result queryItemByPage(int current,int size) throws Exception {
        Page<article> pa = Page.of(current, size);
        pa.addOrder(new OrderItem("update_time",false));
        // 1.分页查询
        articleService.page(pa);
        page<article> p = new page<>(pa.getTotal(),pa.getRecords());
        // 2.封装并返回
        return Result.success(p);
    }


}
