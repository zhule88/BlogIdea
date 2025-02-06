package com.user.controller;

import com.common.pojo.Result;
import com.user.pojo.like;
import com.user.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
@Tag(name = "点赞管理")
public class LikeController {
    @Autowired
    LikeService likeService;

    @Operation(summary = "改变点赞")
    @PutMapping("/change")
    public Result change(@RequestBody like llike) {
        like l = likeService.lambdaQuery().eq(like::getCommentId, llike.getCommentId())
                .eq(like::getUserId, llike.getUserId()).one();
        if(l == null){
            likeService.save(llike);
        }else{
            likeService.removeById(l.getId());
        }
        return Result.success();
    }
    @Operation(summary = "查询用户点赞过的评论")
    @GetMapping("/user")
    public Result user(int id) {
       /* likeService.lambdaUpdate().ge(like::getId,0).remove();*/
        return Result.success(likeService.lambdaQuery().eq(like::getUserId, id).list());
    }

}
