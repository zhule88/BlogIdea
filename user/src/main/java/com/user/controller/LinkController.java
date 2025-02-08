package com.user.controller;

import com.common.pojo.Result;

import com.user.pojo.link;
import com.user.pojo.message;
import com.user.service.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;



@RestController
@RequestMapping("/link")
@Tag(name = "友链管理")
public class LinkController {
    @Autowired
    LinkService linkService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    String redisKey = "linkList";

   /* @SuppressWarnings("unchecked")*/
    @Operation(summary = "查询友链表")
    @GetMapping("/list")
    public Result list() {
       /* List<link> cachedLinks = (List<link>) redisTemplate.opsForValue().get(redisKey);
        if (cachedLinks == null || cachedLinks.isEmpty()) {
            List<link> l = linkService.list();
            redisTemplate.opsForValue().set(redisKey, l);
            redisTemplate.expire(redisKey, 1, TimeUnit.DAYS);
            return Result.success(l);
        }
            return Result.success(cachedLinks);*/
        return  Result.success(linkService.list());
    }

    @Operation(summary = "新增友链")
    @PostMapping("/add")
    public Result add(@RequestBody link link) {
        link.setCreateTime(LocalDateTime.now());
        linkService.save(link);
        return Result.success();
    }

    @Operation(summary = "通过友链申请")
    @GetMapping("/update")
    public Result update( Integer id,  String email)  {
        rabbitTemplate.convertAndSend("email","email",
                new message(email,"<div style=\"background: #4caf50; display: flex; flex-direction: column; justify-content: center; align-items: center; height: 200px; width: 100%; padding: 20px; border-radius: 8px;\">" +
                        "<div style=\"font-size: 24px; color: white; margin-bottom: 10px;\">" +
                        "\uD83D\uDC4F 恭喜，您的友链申请已通过" +
                        "</div>" +
                        "<div style=\"font-size: 16px; color: white;\">" +
                        "感谢您的支持与信任！" +
                        "</div>" +
                        "</div>"));
        linkService.lambdaUpdate().eq(link::getId,id).set(link::getState,1).update();
        return Result.success();
    }

    @Operation(summary = "删除友链")
    @DeleteMapping("/del")
    public Result del(int id) {
        linkService.removeById(id);
        return Result.success();
    }




}
