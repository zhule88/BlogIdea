package com.user.controller;

import com.pojo.Result;
import com.user.pojo.link;
import com.user.service.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/link")
@Tag(name = "友联管理")
public class LinkController {

    @Autowired
    LinkService linkService;

    @Operation(summary = "查询友联表")
    @GetMapping("/list")
    public Result list(){
        return Result.success(linkService.list());
    }

    @Operation(summary = "新增友联")
    @PostMapping("/add")
    public Result add(@RequestBody link link){
        link.setStats(0);
        linkService.save(link);
        return Result.success();
    }

    @Operation(summary = "修改友联")
    @PutMapping("/update")
    public Result update(@RequestBody link link){
        linkService.updateById(link);
        return Result.success();
    }
    @Operation(summary = "删除友联")
    @DeleteMapping("/del")
    public Result del(int id){
        linkService.removeById(id);
        return Result.success();
    }

}
