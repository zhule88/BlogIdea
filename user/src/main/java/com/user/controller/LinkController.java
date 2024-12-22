package com.user.controller;

import com.pojo.Result;
import com.user.pojo.link;
import com.user.service.LinkService;
import com.user.service.MailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/link")
@Tag(name = "友链管理")
public class LinkController {

    @Autowired
    LinkService linkService;
    @Autowired
    MailService mailService;

    @Operation(summary = "查询友链表")
    @GetMapping("/list")
    public Result list() {
        return Result.success(linkService.list());
    }

    @Operation(summary = "新增友链")
    @PostMapping("/add")
    public Result add(@RequestBody link link) {
        link.setCreateTime(LocalDateTime.now());
        linkService.save(link);
        return Result.success();
    }

    @Operation(summary = "修改友链")
    @GetMapping("/update")
    public Result update( Integer id,  String email) throws Exception {

        mailService.send(email,"\uD83D\uDC4F恭喜，您的友链申请已通过");
        linkService.lambdaUpdate().eq(link::getId,id).set(link::getState,1).update();
        return Result.success();
    }

    @Operation(summary = "删除友链")
    @DeleteMapping("/del")
    public Result del(int id) {
        linkService.removeById(id);
        return Result.success();
    }



    @Operation(summary = "测试")
    @GetMapping("/test")
    public Result test() throws Exception {
        mailService.send("ely.sia@qq.com","验证码是114514");
        return Result.success();
    }
}
