package com.article.controller;

import com.article.service.CategoryService;
import com.pojo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@Tag(name = "文章分类管理")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @Operation(summary = "查询分类表")
    @GetMapping("/list")
    public Result list(){
        return Result.success(categoryService.list());
    }
}
