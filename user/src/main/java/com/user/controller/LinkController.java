package com.user.controller;

import com.pojo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/link")
@Tag(name = "友联管理")
public class LinkController {
    @Operation(summary = "查询标签表")
    @GetMapping("/list")
    public Result list(){
        return Result.success("你哈");
    }

}
