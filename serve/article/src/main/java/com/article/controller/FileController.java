package com.article.controller;


import com.article.pojo.file;
import com.article.service.FileService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.pojo.Result;
import io.minio.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/file")
@Tag(name = "文件管理")

public class FileController {
    final String bucketName = "article" ;

    @Autowired
    MinioClient minioClient;
    @Autowired
    FileService fileService;

    @Operation(summary = "获取文章所有文件名")
    @GetMapping("/list")
    public Result list(int id) {
        return Result.success(fileService.list(new LambdaQueryWrapper<file>().eq(file::getArticleId, id)));
    }

    @Operation(summary = "上传文件")
    @PostMapping("/add")
    public Result add(@RequestParam("file") MultipartFile file,
                      @RequestParam(required = false,value = "id") Integer articleId) throws Exception {
      String filename =  fileService.getFileName(file);
      if(articleId != null) {
          fileService.addSql(filename, articleId);
      }
        return Result.success(fileService.addFile(file,filename));
    }
    @Operation(summary = "删除单个文件")
    @DeleteMapping("/del")
    public Result del(String filename) throws Exception {
        int idx= filename.lastIndexOf("/");
        String name = filename.substring(idx + 1);
        minioClient.removeObject(RemoveObjectArgs.builder()
                        .bucket(bucketName)
                        .object(name)
                        .build());
        fileService.remove(new LambdaQueryWrapper<file>().eq(file::getFilename,filename));
        return Result.success();
    }

    @Operation(summary = "删除所有文件")
    @DeleteMapping("/delall")
    public Result delall(int id) throws Exception {
        fileService.delall(id);
        return Result.success();
    }

}
