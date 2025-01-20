package com.user.controller;


import com.common.pojo.Result;

import com.user.pojo.*;
import com.user.service.CommentService;

import com.user.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/comment")
@Tag(name = "留言管理")
public class CommentController {

    @Autowired
    CommentService commentService;




    @Operation(summary = "根据文章id返回父评论")
    @GetMapping("/page")
    public Result page(int id,int current,int size) {
        return Result.success(commentService.page(id,current,size));
    }

      @Operation(summary = "根据父评论id集合查询子评论")
    @PostMapping("/list")
    public Result getByIds(@RequestBody List<Integer> ids) {
        if(ids.isEmpty()){
            return Result.success();
        }
          List<comment> l = commentService.lambdaQuery().in(comment::getParentId,ids).list();
          /*commentService.lambdaUpdate().ge(comment::getId,0).remove();*/
        return Result.success(commentService.setUser(l));
    }

    @Operation(summary = "查询文章下留言总数")
    @GetMapping("/count")
    public Result count(int id) {
        return Result.success(commentService.lambdaQuery().eq(comment::getArticleId,id).count());
    }


    @Operation(summary = "新增留言")
    @PostMapping("/add")
    public Result add(@RequestBody comment comment) {
        comment.setCreateTime(LocalDateTime.now());
        commentService.save(comment);
        return Result.success();
    }



    @Operation(summary = "删除留言")
    @DeleteMapping("/del")
    public Result del(int id) {
        commentService.removeById(id);
        return Result.success();
    }
}
