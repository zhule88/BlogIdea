package com.user.controller;

import com.common.pojo.Result;
import com.common.service.MinioService;
import com.user.pojo.cover;
import com.user.service.CommentService;
import com.user.service.CoverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/cover")
@Tag(name = "主页轮播背景管理")
public class CoverController {

    @Autowired
    CoverService coverService;

    MinioService minioService;
    @Autowired
    public CoverController(MinioService minioService) {
        this.minioService = minioService;
        this.minioService.setBucketName("cover");
    }

    @Operation(summary = "查询轮播图")
    @GetMapping("/list")
    public Result list(@RequestParam(required = false) Integer state) {
        if(state == null){
            return Result.success(coverService.list());
        }
        return Result.success(coverService.lambdaQuery().eq(cover::getState, state).list());
    }

    @Operation(summary = "新增轮播图")
    @GetMapping("/add")
    public Result add(MultipartFile file) throws Exception {
        cover cover = new cover();
        String url = minioService.addFile(file);
        cover.setUrl(url);
        cover.setState(1);
        cover.setCreateTime(LocalDateTime.now());
        coverService.save(cover);
        return Result.success();
    }


    @Operation(summary = "新增轮播图")
    @GetMapping("/del")
    public Result del(int id) throws Exception {
        String url = coverService.getById(id).getUrl();
        String filename = url.substring(url.lastIndexOf("/")+1);
        minioService.del(filename);
        return Result.success();
    }

    @Operation(summary = "改变轮播图状态")
    @PutMapping("/update")
    public Result updateState(int id, int  state) {
        coverService.lambdaUpdate().eq(cover::getId, id).set(cover::getState, state).update();
        return Result.success();
    }
}