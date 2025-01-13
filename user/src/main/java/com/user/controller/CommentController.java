package com.user.controller;

import com.common.pojo.Result;
import com.user.pojo.comment;
import com.user.pojo.link;
import com.user.pojo.message;
import com.user.service.CommentService;
import com.user.service.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/comment")
@Tag(name = "留言管理")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Operation(summary = "新增友链")
    @PostMapping("/add")
    public Result add(@RequestBody comment comment) {
        comment.setCreateTime(LocalDateTime.now());
        commentService.save(comment);
        return Result.success();
    }

    @Operation(summary = "删除友链")
    @DeleteMapping("/del")
    public Result del(int id) {
        commentService.removeById(id);
        return Result.success();
    }
}
