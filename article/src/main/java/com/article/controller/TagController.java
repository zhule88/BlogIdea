package com.article.controller;


import com.article.service.TagService;


import com.common.pojo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/tag")
@Tag(name = "标签管理")
public class TagController {
    @Autowired
    TagService tagService;


    @Operation(summary = "查询标签表")
    @GetMapping("/list")
    public Result list(){
        return Result.success(tagService.list());
    }





}
